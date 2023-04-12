pipeline {
    agent any
    tools {
        jdk 'Java17'
    }
    options {
        gitLabConnection('Gitlab')
    }
    triggers {
        gitlab(
                triggerOnPush: true,
                triggerOnMergeRequest: true,
                branchFilterType: 'All',
                triggerOnNoteRequest: true,
                noteRegex: "Jenkins please retry a build",
                addNoteOnMergeRequest: true,
                setBuildDescription: true,
                addCiMessage: true,
                addVoteOnMergeRequest: true)
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile test'
            }
        }
        stage('Dependency Check ') {
            parallel {

                stage("Dependency Check") {
                    when {
                        expression { return (env.GIT_BRANCH =~ '.*develop.*|.*master.*').matches() }
                    }

                    steps {
                        dependencyCheck additionalArguments: '''
                    -o "./"
                    -s "./"
                    -f "ALL"
                    --prettyPrint''', odcInstallation: 'de-check'
                        dependencyCheckPublisher pattern: 'dependency-check-report.xml'
                    }
                }
            }
        }
        stage('Sonar') {

        environment {
            scannerHome = tool 'sonar_scanner'
        }
            when {
                expression { return (env.GIT_BRANCH =~ '.*develop.*|.*master.*').matches() }
            }

            steps {
                withCredentials([string(credentialsId: 'sonar', variable: 'TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.host.url=https://sonar.sdf.x-hub.io ' +
                            "-Dsonar.login=$TOKEN"
                    // archiveArtifacts allowEmptyArchive: true, onlyIfSuccessful: false
                }
            }
        }

        stage('Snapshot And Deploy') {
            when {
                expression {
                    return (!(env.GIT_BRANCH =~ '^origin/.*').matches() && !(env.GIT_BRANCH =~ '.*master.*').matches()) || ((env.GIT_BRANCH =~ 'origin/develop').matches())
                }
            }
            steps {
                sh 'mvn -DskipTests clean deploy'
                sh """
        if [ ! -d "/tmp/deploy" ] ; then
            git clone git@gitlab.com:xhub-org/p/tooling/deploy-playbooks.git /tmp/deploy
        else
            cd /tmp/deploy
            git pull
        fi
        ansible-playbook /tmp/deploy/smwall-backend/site.yml -i /tmp/deploy/inventory/dev --extra-vars "mvn_version=${
                    getCurrentVersion()
                }"
    """
            }
        }

        stage('Release') {
            when {
                expression { return (env.GIT_BRANCH =~ '.*master.*').matches() }
            }
            steps {
                releaseCurrentVersion()
            }
        }

    }
    post {
        failure {
            script {
                if ((env.GIT_BRANCH =~ '.*develop.*|.*master.*').matches()) {
                    // slackSend(channel: 'ticket-sadaka', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")

                } else {
                    addGitLabMRComment comment: ":no_entry_sign: Jenkins Build FAILURE\n\nResults available at: [Jenkins [$env.JOB_NAME#$env.BUILD_NUMBER]]($env.BUILD_URL)"
                    updateGitlabCommitStatus name: "build", state: 'failed'
                }
            }
        }
        success {
            script {
                if ((env.GIT_BRANCH =~ '.*develop.*|.*master.*').matches()) {
                    // slackSend(channel: 'ticket-sadaka', color: '#008000', message: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")

                } else {
                    addGitLabMRComment comment: ":white_check_mark: Jenkins Build SUCCESS\n\nResults available at: [Jenkins [$env.JOB_NAME#$env.BUILD_NUMBER]]($env.BUILD_URL)"
                    updateGitlabCommitStatus name: "build", state: 'success'
                }
            }
        }

        aborted {
            addGitLabMRComment comment: ":point_up: Jenkins Build ABORTED\n\nResults available at: [Jenkins [$env.JOB_NAME#$env.BUILD_NUMBER]]($env.BUILD_URL)"
            updateGitlabCommitStatus name: "build", state: 'canceled'
        }
        unstable {
            addGitLabMRComment comment: ":warning: Jenkins Build UNSTABLE\n\nResults available at: [Jenkins [$env.JOB_NAME#$env.BUILD_NUMBER]]($env.BUILD_URL)"
            updateGitlabCommitStatus name: "build", state: 'failed'
        }
        always {
            cleanWs()
        }
    }
}

def getCurrentVersion() {
    def pom = readMavenPom file: 'pom.xml'
    def currentVersion = pom.version

    return currentVersion
}

def releaseCurrentVersion() {

    def currentVersion = getCurrentVersion().minus('-SNAPSHOT')

    input "Are you sure to Release ? ${currentVersion}"

    prepareAndDeploy(currentVersion)

}

def prepareAndDeploy(newVersion) {
    echo "######### deploy To nexus"

    sh "mvn -Dmaven.test.skip=true versions:set -DnewVersion=\"${newVersion}.RELEASE\" versions:commit"

    sh 'mvn -Dmaven.test.skip=true clean deploy '

    echo "########## branching Tag"

    sh "git commit --allow-empty -am \" Releasing version ${newVersion}\""

    sh "git tag -a \"${newVersion}\" -m \"Tagging version ${newVersion}\""


    pushCode("git push --tags")

    nextDevSnapshot()

}

def pushCode(cmd) {
    String credentialsId = "Gilab_SSH"
    sshagent([credentialsId]) {
        sh(script: "${cmd}")
    }
}

def nextDevSnapshot() {
    echo "######### prepare next version SNAPSHOT #######"
    sh "git checkout ${BRANCH_NAME}"
    def n0 = "\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion}"
    def newDevVersionID = input id: 'newDevVersionID', message: 'Specify version or let empty to be autoincrement', parameters: [string(defaultValue: '', description: 'write version like M.m.p ex: 1.0.0', name: 'newDevVersion')]

    if (newDevVersionID?.trim()) {
        n0 = newDevVersionID
    }
    sh "git checkout -b new-dev-${n0}"
    sh "mvn -DskipTests build-helper:parse-version versions:set -DnewVersion=${n0}-SNAPSHOT versions:commit"
    sh "git commit --allow-empty -am \"Prepare new snapshot ${n0}\""
    pushCode("git push -o merge_request.create -o merge_request.target=develop -o merge_request.title=\"New Snapshot ${n0}\" -o merge_request.remove_source_branch origin new-dev-${n0}")
}
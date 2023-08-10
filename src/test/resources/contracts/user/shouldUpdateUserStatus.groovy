package contracts.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update user status"
    request {
        method POST()
        url '/api/v1/users/{userId}/activate-deactivate-user'
        headers {
            contentType applicationJson()
        }
    }
    response {
        status 200
        headers {
            contentType applicationJson()
        }
    }

}
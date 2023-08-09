package contracts.user

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description 'should get all users'

    request {
        method GET()
        url '/api/v1/users'
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body([
                [
                        id         : "1",
                        firstName  : "User1",
                        lastName   : "UserLastName1",
                        email      : "user1@gmail.com",
                        authorities: [
                                [id: "authorityId1", name: "ROLE_ADMIN"]
                        ]
                ],
                [
                        id         : "2",
                        firstName  : "User2",
                        lastName   : "UserLastName2",
                        email      : "user2@gmail.com",
                        authorities: [
                                [id: "authorityId2", name: "ROLE_USER"]
                        ]
                ]
        ])
    }
}
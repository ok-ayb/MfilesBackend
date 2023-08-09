package contracts.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should create a new user"
    request {
        url "/api/v1/users"
        method POST()
        headers {
            contentType applicationJson()
        }
        body(
                firstName: "testUser",
                lastName: "testUser",
                email: "testUser@gmail.com",
                authorities: [
                        [
                                id  : 'authorityId1',
                                name: 'ADMIN'
                        ]
                ]
        )
    }
    response {
        status 201
        headers {
            contentType applicationJson()
        }
        body(
                id: "1234",
                firstName: "testUser",
                lastName: "testUser",
                email: 'testUser@gmail.com',
                authorities: [
                        [
                                id  : 'authorityId1',
                                name: 'ADMIN'
                        ]
                ]
        )
    }
}
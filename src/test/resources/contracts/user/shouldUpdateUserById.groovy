package contracts.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update User"
    request {
        url "/api/v1/users/userId"
        method PATCH()
        headers {
            contentType applicationJson()
        }
        body(
                firstName: $(consumer('NewFirstName'), producer('ApprovedFirstName')),
                lastName: $(consumer('NewLastName'), producer('ApprovedLastName')),
                email: $(consumer('email@example.com'), producer('approved-email@example.com')),
                authorities: [
                        id: '123',
                        name: 'ROLE_ADMIN'
                ]
        )
    }
    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body(
                firstName: $(consumer('NewFirstName'), producer('ApprovedFirstName')),
                lastName: $(consumer('NewLastName'), producer('ApprovedLastName')),
                email: $(consumer('email@example.com'), producer('approved-email@example.com')),
                authorities: [
                        id: '123',
                        name: 'ROLE_ADMIN'
                ]
        )
    }
}

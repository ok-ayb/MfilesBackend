package contracts.authority


import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description 'should get all authorities'

    request {
        method GET()
        url '/api/v1/authorities'
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
                        id  : "1",
                        name: "ROLE_ADMIN",
                ],
                [
                        id  : "2",
                        name: "ROLE_MODERATOR",
                ]
        ])
    }
}
package contracts.wallSettings

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name("get latest wall setting")
    description("Should get the latest wall settings")
    request {
        method 'GET'
        url '/api/v1/wall/settings/latest'
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(
                id: $(consumer('id')),
                title: $(consumer('title')),
                filename: $(consumer('filename')),
                logoBase64: $(consumer('logoBase64'))
        )
    }
}

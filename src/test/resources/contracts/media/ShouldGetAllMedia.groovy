package contracts.media

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should get all media"
    request {
        url "/api/v1/media"
        method GET()
        headers {
            contentType applicationJson()
        }
    }
    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body([
                [
                        "id"  : "id1",
                        "text": "text1"
                ],
                [
                        "id"  : "id2",
                        "text": "text2"
                ]
        ])
    }
}

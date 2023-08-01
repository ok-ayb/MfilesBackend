package contracts.announcement

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should add Wall Setting"
    request {
        url "/api/v1/settings"
        method POST()
        headers {
            contentType('multipart/form-data')
        }
        multipart(
                title: value("Sample Wall Title"),
                logo: named(
                        name: value("logo.png"),
                        content: value("@bytes(3)"),
                        contentType: value("image/png")
                )
        )
    }
    response {
        status 201
        headers {
            contentType applicationJson()
        }
        body(
                id: "test",
                title: "Sample Wall Title",
                logo: [
                        originalFilename: "logo.png",
                        contentType     : "image/png",
                        bytes           : "@bytes(3)"
                ]
        )
    }
}
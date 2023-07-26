package contracts.announcement

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should add Announcement"
    request {
        url "/api/v1/announcements"
        method POST()
        headers {
            contentType applicationJson()
        }
        body(
                "title":"valid Title",
                "description":"This is a test announcement description",
                "endDate":"2043-07-20T12:00:00Z",
                "startDate":"2041-08-21T12:00:00Z"
        )
    }
    response {
        status 201
        headers {
            contentType applicationJson()
        }
        body(
                id:"12345",
                "title":"valid Title",
                "description":"This is a test announcement description",
                "endDate":"2043-07-20T12:00:00Z",
                "startDate":"2041-08-21T12:00:00Z"
        )
    }
}
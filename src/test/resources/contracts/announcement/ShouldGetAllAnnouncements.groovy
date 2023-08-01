package contracts.announcement

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description 'should get all announcements'

    request {
        method GET()
        url '/api/v1/announcements'
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
                        id: "1",
                        title: "Announcement 1",
                        description: "Description 1",
                        startDate: "2023-07-20T00:00:00Z",
                        endDate: "2023-07-20T01:00:00Z"
                ],
                [
                        id: "2",
                        title: "Announcement 2",
                        description: "Description 2",
                        startDate: "2023-07-20T02:00:00Z",
                        endDate: "2023-07-20T02:15:00Z"
                ]
        ])
    }
}
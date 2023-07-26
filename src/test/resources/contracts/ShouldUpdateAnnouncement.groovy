package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update Announcement"
    request {
        url "/api/v1/announcements/announcementId"
        method PATCH()
        headers {
            contentType applicationJson()
        }
        body(
                title: $(consumer('Updated Title'), producer('Approved Title')),
                description: $(consumer('Updated Announcement Description'), producer('Approved Description')),
                endDate: Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", '2061-07-26T12:00:00Z'),
                startDate: Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", '2060-08-02T12:00:00Z')
        )
    }
    response {
        status 200
        headers {
            contentType applicationJson()
        }
        body(
                id: $(consumer('announcementId'), producer('announcementId')),
                title: $(consumer('Updated Title'), producer('Approved Title')),
                description: $(consumer('Updated Announcement Description'), producer('Approved Description')),
                endDate: Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", '2061-07-26T12:00:00Z'),
                startDate: Date.parse("yyyy-MM-dd'T'HH:mm:ss'Z'", '2060-08-02T12:00:00Z')
        )
    }
}

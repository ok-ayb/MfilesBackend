package contracts.announcement

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should delete an announcement"

    request {
        method DELETE()
        headers {
            contentType applicationJson()
        }
        url "/api/v1/announcements/announcementId"
    }

    response {
        status OK()
    }

}

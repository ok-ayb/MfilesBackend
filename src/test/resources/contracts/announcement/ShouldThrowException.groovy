package contracts.announcement

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should throw exception when deleting an Announcement with invalid ID"

    request {
        method DELETE()
        headers {
            contentType applicationJson()
        }
        url "/api/v1/announcements/invalidId"
    }

    response {
        status NOT_FOUND()
    }
}
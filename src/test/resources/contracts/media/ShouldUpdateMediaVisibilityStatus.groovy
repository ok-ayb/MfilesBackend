package contracts.media
import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "should update media visibility status"

    request {
        method 'PUT'
        url '/api/v1/media/{mediaId}/visibility'
        headers {
            contentType applicationJson()
        }
    }

    response {
        status 200
        headers {
            contentType applicationJson()
        }
    }
}

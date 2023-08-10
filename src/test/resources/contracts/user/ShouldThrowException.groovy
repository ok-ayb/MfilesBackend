package contracts.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'DELETE'
        url '/api/v1/users/invalidId'
    }
    response {
        status 404
        body(
                key: 'user.not.found',
                code: 7,
                messageKeyParameters: [],
                payload: null
        )
        headers {
            contentType applicationJson()
        }
    }
}

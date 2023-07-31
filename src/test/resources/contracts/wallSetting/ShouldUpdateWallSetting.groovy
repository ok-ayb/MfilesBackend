package contracts.wallSetting

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should update Wall Setting"
    request {
        url "/api/v1/settings/wallSettingId"
        method PATCH()
        headers {
            contentType applicationJson()
        }
        body(
                title: $(consumer('Updated Title'), producer('Approved Title')),
                filename: $(consumer('filename'), producer('filename')),
                logoBase64: $(consumer('logoBase64'), producer('logoBase64'))
        )
    }
    response {
        status OK()
    }
}

package contracts.user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should delete user"

    request {
        method DELETE()
        headers {
            contentType applicationJson()
        }
        url "/api/v1/users/userId"
    }

    response {
        status OK()
    }

}

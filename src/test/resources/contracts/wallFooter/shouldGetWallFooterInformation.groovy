package contracts.wallFooter

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should Get wall Footer informatrions"
    request {
        url "/api/v1/footer"
        method GET()
    }
    response {
        status 200
        body([
                id: "1",
                coOrganizer: "Organizer",
                institutionalPartners: ['Partner 1', 'Partner 2'],
                sponsors: [
                        [name: "Sponsor 1", type: "Type 1", logoUrl: "http://sponsor1.com/logo"],
                        [name: "Sponsor 2", type: "Type 2", logoUrl: "http://sponsor2.com/logo"]
                ],
                logoUrl: "http://example.com/logo"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}

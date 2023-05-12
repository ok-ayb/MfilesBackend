package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.service.WebhooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Webhooks Management Resource")
@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.WEBHOOKS)
@RequiredArgsConstructor
public class WebhooksController {

    private final WebhooksService webhooksService;

    @ApiOperation(value = "Webhook Subscription handler")
    @GetMapping(ApiPaths.META)
    public ResponseEntity<String> handleMetaSubscription(@RequestParam(name = "hub.mode") String mode,
                                                         @RequestParam(name = "hub.verify_token") String verifyToken,
                                                         @RequestParam(name = "hub.challenge") String challenge) {
        webhooksService.verifyMetaSubscription(mode, verifyToken);
        return ResponseEntity.ok().body(challenge);
    }

    @ApiOperation(value = "Handle Meta field update")
    @PostMapping(ApiPaths.META)
    public ResponseEntity<?> handleMetaUpdate(@RequestBody String payload) {
        webhooksService.handleMetaUpdate(payload);
        return ResponseEntity.ok().build();
    }

}

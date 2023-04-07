package smwall.smbackend.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smwall.smbackend.holders.ApiPaths;
import smwall.smbackend.service.WebhooksService;

@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.WEBHOOKS)
@RequiredArgsConstructor
public class WebhooksController {

    private final WebhooksService webhooksService;

    @GetMapping(ApiPaths.META)
    public ResponseEntity<String> handleMetaSubscriptionRequest(@RequestParam(name = "hub.mode") String mode,
                                                                @RequestParam(name = "hub.verify_token") String verifyToken,
                                                                @RequestParam(name = "hub.challenge") String challenge) {
        webhooksService.verifyMetaSubscription(mode, verifyToken);
        return ResponseEntity.status(HttpStatus.OK).body(challenge);
    }

    @PostMapping(ApiPaths.META)
    public ResponseEntity<?> handleMetaUpdate(@RequestBody String requestBody) {
        webhooksService.handleMetaUpdate(requestBody);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

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

    @ApiOperation(value = "Handle Meta Subscription")
    @GetMapping(ApiPaths.META)
    public ResponseEntity<String> handleMetaSubscription(@RequestParam(name = "hub.mode") String mode,
                                                         @RequestParam(name = "hub.verify_token") String verifyToken,
                                                         @RequestParam(name = "hub.challenge") String challenge) {
        webhooksService.verifyMetaNotification(mode, verifyToken);
        return ResponseEntity.ok().body(challenge);
    }

    @ApiOperation(value = "Handle Meta notification")
    @PostMapping(ApiPaths.META)
    public void handleMetaNotification(@RequestBody String payload) {
        webhooksService.handleMetaNotification(payload);
    }

    @ApiOperation(value = "Handle YouTube Subscription")
    @GetMapping(ApiPaths.YOUTUBE)
    public ResponseEntity<String> handleYouTubeSubscription(@RequestParam(name = "hub.mode") String mode,
                                                            @RequestParam(name = "hub.verify_token") String verifyToken,
                                                            @RequestParam(name = "hub.challenge") String challenge) {
        webhooksService.verifyYouTubeSubscription(mode, verifyToken);
        return ResponseEntity.ok().body(challenge);
    }

    @ApiOperation(value = "Handle YouTube notification")
    @PostMapping(ApiPaths.YOUTUBE)
    public void handleYouTubeUpdate(@RequestBody String payload) {
        webhooksService.handleYouTubeNotification(payload);
    }

}

package io.xhub.smwall.service;

import io.xhub.smwall.config.MetaProperties;
import io.xhub.smwall.config.YoutubeProperties;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhooksService {
    private final MetaProperties metaProperties;
    private final YoutubeProperties youtubeProperties;

    public void verifyMetaNotification(String mode, String verifyToken) {
        log.info("Start verifying Meta subscription with mode {} and verifyToken {} :", mode, verifyToken);
        verifySubscription(mode, verifyToken, metaProperties.getVerifyToken());
    }

    public void handleMetaNotification(String payload) {
        log.info("Handling Meta notification: " + payload);
    }

    public void verifyYouTubeSubscription(String mode, String verifyToken) {
        log.info("Start verifying subscription with mode {} and verifyToken {} :", mode, verifyToken);
        verifySubscription(mode, verifyToken, youtubeProperties.getVerifyToken());
    }

    public void handleYouTubeNotification(String payload) {
        log.info("Handling YouTube notification: " + payload);
    }

    private void verifySubscription(String mode, String verifyToken, String tokenToVerifyWith) {
        if (!StringUtils.equals(mode, "subscribe") || !verifyToken.equals(tokenToVerifyWith)) {
            throw new BusinessException(ApiClientErrorCodes.WEBHOOKS_SUBSCRIPTION_FAILED.getErrorMessage());
        }
    }

}

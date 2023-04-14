package io.xhub.smwall.smbackend.service;

import io.xhub.smwall.smbackend.config.MetaProperties;
import io.xhub.smwall.smbackend.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import io.xhub.smwall.smbackend.holders.ApiClientErrorCodes;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhooksService {

    private final MetaProperties metaProperties;

    public void verifyMetaSubscription(String mode, String verifyToken) {
        log.info("Start verifying subscription with mode {} and verifyToken {} :", mode, verifyToken);
        if (!StringUtils.equals(mode, "subscribe") || !verifyToken.equals(this.metaProperties.getVerifyToken())) {
            throw new BusinessException(ApiClientErrorCodes.WEBHOOKS_SUBSCRIPTION_DENIED.getErrorMessage());
        }
    }

    public void handleMetaUpdate(String payload) {
        log.info("Meta request body: " + payload);

        /**
         * TODO: - Business logic for handling meta update
         *       - Analyse the content and save them to database if necessary
         *       - This can be sentiment analysis, storing to database, etc.
         */
    }

}

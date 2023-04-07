package smwall.smbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import smwall.smbackend.config.MetaProperties;
import smwall.smbackend.exceptions.BusinessException;
import smwall.smbackend.holders.ApiClientErrorCodes;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhooksService {

    private final MetaProperties metaProperties;

    public void verifyMetaSubscription(String mode, String verifyToken) {
        if (!mode.equals("subscribe") || !verifyToken.equals(this.metaProperties.getVerifyToken())) {
            throw new BusinessException(ApiClientErrorCodes.WEBHOOKS_SUBSCRIPTION_DENIED.getErrorMessage());
        }
    }

    public void handleMetaUpdate(String requestBody) {
        log.info("Meta request body: " + requestBody);

        /**
         * TODO: - Business logic for handling meta update
         *       - Analyse the content and save them to database if necessary
         *       - This can be sentiment analysis, storing to database, etc.
         */
    }

}

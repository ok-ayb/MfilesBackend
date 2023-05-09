package io.xhub.smwall.holders;

import io.xhub.smwall.exceptions.ErrorMessage;

import java.util.Map;

public enum ApiClientErrorCodes {
    WEBHOOKS_SUBSCRIPTION_FAILED(1, "webhooks.subscription.failed"),
    PROFILE_PICTURE_EXTRACTION_FAILED(2, "profile.picture.extraction.failed"),
    WALL_HEADER_NOT_FOUND(3, "wall.header.not.found");

    private final Integer code;
    private final String messageKey;

    ApiClientErrorCodes(Integer code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public ErrorMessage getErrorMessage(Map<String, Object> payload, Object... params) {
        return ErrorMessage.builder()
                .code(code)
                .key(messageKey)
                .messageKeyParameters(params)
                .payload(payload)
                .build();
    }

    public ErrorMessage getErrorMessage(Object... params) {
        return ErrorMessage.builder()
                .code(code)
                .key(messageKey)
                .messageKeyParameters(params)
                .build();
    }
}
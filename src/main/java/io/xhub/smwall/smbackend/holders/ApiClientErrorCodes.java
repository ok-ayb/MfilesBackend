package io.xhub.smwall.smbackend.holders;

import io.xhub.smwall.smbackend.exceptions.ErrorMessage;

import java.util.Map;

public enum ApiClientErrorCodes {

    //user *example*
    USER_NOT_FOUND(1, "user.not.found"),

    WEBHOOKS_SUBSCRIPTION_DENIED(2, "webhooks.subscription.failed");


    private Integer code;
    private String msgKey;

    ApiClientErrorCodes(Integer code, String msgKey) {
        this.code = code;
        this.msgKey = msgKey;
    }

    public ErrorMessage getErrorMessage(Map<String, ?> payload, Object... params) {
        return ErrorMessage.builder()
                .code(code)
                .key(msgKey)
                .messageKeyParameters(params)
                .payload(payload)
                .build();
    }

    public ErrorMessage getErrorMessage(Object... params) {
        return ErrorMessage.builder()
                .code(code)
                .key(msgKey)
                .messageKeyParameters(params)
                .build();
    }

}
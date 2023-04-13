package io.xhub.smwall.smbackend.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getKey());
        this.errorMessage = errorMessage;
    }

    public BusinessException(String message, ErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public BusinessException(String message, Throwable cause, ErrorMessage errorMessage) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }

    public BusinessException(Throwable cause, ErrorMessage errorMessage) {
        super(cause);
        this.errorMessage = errorMessage;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorMessage errorMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessage = errorMessage;
    }

}

package io.xhub.smwall.utlis;

import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.exceptions.BusinessException;

import java.time.Instant;
import java.util.regex.Pattern;

public interface AssertUtils {
    static void assertIsAfterToday(Instant date) {
        if (date.isBefore(Instant.now())) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertIsAfterDate(Instant startDate, Instant endDate) {
        if (!endDate.isAfter(startDate)) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertPattern(String value, String pattern) {
        if (!Pattern.matches(pattern, value)) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertNotNull(Object data) {
        if (data == null) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }
}
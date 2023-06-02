package io.xhub.smwall.utlis;

import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.exceptions.BusinessException;

import java.time.Instant;
import java.util.regex.Pattern;

public interface AssertUtils {

    static void assertIsAfterToday(Instant date) {
        Instant currentTime = Instant.now();
        if (date.isBefore(currentTime)) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertIsAfterStartDate(Instant startDate, Instant endDate) {
        if (!endDate.isAfter(startDate)) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertPattern(String value, String pattern) {
        if (value != null && !Pattern.matches(pattern, value)) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertNotNull(Instant date) {
        if (date == null) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

    static void assertNotBlank(String text) {
        if (text == null) {
            throw new BusinessException(ApiClientErrorCodes.INVALID_COMMAND_ARGS.getErrorMessage());
        }
    }

}

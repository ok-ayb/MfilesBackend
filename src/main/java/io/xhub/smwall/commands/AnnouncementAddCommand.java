package io.xhub.smwall.commands;

import io.xhub.smwall.constants.FormValidationCodes;
import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.validation.constrainsts.isAfterToday;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;


@Getter
@RequiredArgsConstructor
public class AnnouncementAddCommand {

    @NotNull
    @Pattern(regexp = RegexPatterns.ANNOUNCEMENT_TITLE, message = FormValidationCodes.INVALID_TITLE_PATTERN)
    private final String title;

    @NotNull
    @Pattern(regexp = RegexPatterns.ANNOUNCEMENT_DESCRIPTION, message = FormValidationCodes.INVALID_DESCRIPTION_PATTERN)
    private final String description;

    @NotNull
    @isAfterToday(message = FormValidationCodes.START_DATE_AFTER_TODAY)
    private final Instant endDate;

    @NotNull
    @isAfterToday(message = FormValidationCodes.END_DATE_AFTER_TODAY)
    private final Instant startDate;
}


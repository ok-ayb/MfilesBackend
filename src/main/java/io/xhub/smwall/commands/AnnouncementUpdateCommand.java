package io.xhub.smwall.commands;

import io.xhub.smwall.constants.FormValidationCodes;
import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.validation.constrainsts.isAfterToday;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
public class AnnouncementUpdateCommand {

    @Pattern(regexp = RegexPatterns.ANNOUNCEMENT_TITLE, message = FormValidationCodes.INVALID_TITLE_PATTERN)
    private final String title;

    @Pattern(regexp = RegexPatterns.ANNOUNCEMENT_DESCRIPTION, message = FormValidationCodes.INVALID_DESCRIPTION_PATTERN)
    private final String description;

    @isAfterToday(message = FormValidationCodes.START_DATE_AFTER_TODAY)
    private final Instant endDate;

    @isAfterToday(message = FormValidationCodes.END_DATE_AFTER_TODAY)
    private final Instant startDate;
}

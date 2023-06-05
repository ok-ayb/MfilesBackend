package io.xhub.smwall.commands;

import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.validation.validators.Validatable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static io.xhub.smwall.utlis.AssertUtils.*;

@Getter
@RequiredArgsConstructor
public class AnnouncementCommand implements Validatable {
    private final String title;

    private final String description;

    private final Instant startDate;

    private final Instant endDate;

    @Override
    public void validate() {
        assertNotNull(title);
        assertNotNull(description);
        assertNotNull(startDate);
        assertNotNull(endDate);
        assertIsAfterToday(startDate);
        assertIsAfterToday(endDate);
        assertIsAfterDate(startDate, endDate);
        assertPattern(title, RegexPatterns.ANNOUNCEMENT_TITLE);
        assertPattern(description, RegexPatterns.ANNOUNCEMENT_DESCRIPTION);

    }
}


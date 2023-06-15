package io.xhub.smwall.commands;

import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.validation.validators.Validatable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static io.xhub.smwall.utlis.AssertUtils.*;

@Getter
@RequiredArgsConstructor
public class AnnouncementUpdateCommand implements Validatable {
    private final String title;

    private final String description;

    private final Instant endDate;

    @Override
    public void validate() {
        assertNotNull(title);
        assertNotNull(description);
        assertNotNull(endDate);
        assertIsAfterToday(endDate);
        assertPattern(title, RegexPatterns.ANNOUNCEMENT_TITLE);
        assertPattern(description, RegexPatterns.ANNOUNCEMENT_DESCRIPTION);
    }
}
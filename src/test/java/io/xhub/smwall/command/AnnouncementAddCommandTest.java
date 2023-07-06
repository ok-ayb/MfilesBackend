package io.xhub.smwall.command;

import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.constants.FormValidationCodes;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnnouncementAddCommandTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_not_throwsAnyConstraintViolationException_when_validPayload() {
        AnnouncementAddCommand command = new AnnouncementAddCommand(
                "Valid Title",
                "Valid announcement description",
                Instant.now().plusSeconds(3600),
                Instant.now().plusSeconds(7200)
        );

        Set<ConstraintViolation<AnnouncementAddCommand>> violations = validator.validate(command);

        assertEquals(0, violations.size());

    }

    @Test
    public void should_ThrowsConstraintViolationException_when_InvalidTitle() {
        AnnouncementAddCommand command = new AnnouncementAddCommand(
                "", // Invalid empty title
                "Valid announcement description",
                Instant.now().plusSeconds(3600),
                Instant.now().plusSeconds(7200)
        );

        Set<ConstraintViolation<AnnouncementAddCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<AnnouncementAddCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals(FormValidationCodes.INVALID_TITLE_PATTERN, violationMessage);
    }

    @Test
    public void should_ThrowsConstraintViolationException_when_InvalidDescription() {
        AnnouncementAddCommand command = new AnnouncementAddCommand(
                "Valid announcement title",
                "", // Invalid empty description
                Instant.now().plusSeconds(3600),
                Instant.now().plusSeconds(7200)
        );

        Set<ConstraintViolation<AnnouncementAddCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<AnnouncementAddCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals(FormValidationCodes.INVALID_DESCRIPTION_PATTERN, violationMessage);
    }

    @Test
    public void should_ThrowsConstraintViolationException_when_InvalidEndDate() {
        AnnouncementAddCommand command = new AnnouncementAddCommand(
                "Valid announcement title",
                "Valid announcement description",
                Instant.now().plusSeconds(3600),
                Instant.now().minusSeconds(7200)// Invalid end date (before current time)
        );

        Set<ConstraintViolation<AnnouncementAddCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<AnnouncementAddCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals(FormValidationCodes.END_DATE_AFTER_TODAY, violationMessage);
    }

    @Test
    public void should_ThrowsConstraintViolationException_when_InvalidStartDate() {
        AnnouncementAddCommand command = new AnnouncementAddCommand(
                "Valid announcement title",
                "Valid announcement description",
                Instant.now().minusSeconds(3600), // Invalid start date (before current time)
                Instant.now().plusSeconds(7200)
        );

        Set<ConstraintViolation<AnnouncementAddCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<AnnouncementAddCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals(FormValidationCodes.START_DATE_AFTER_TODAY, violationMessage);
    }

}

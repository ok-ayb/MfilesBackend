package io.xhub.smwall.command;

import io.xhub.smwall.commands.WallSettingAddCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WallSettingAddCommandTest {
    private Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    @Test
    public void should_ThrowNothing_when_ValidCommand() {
        WallSettingAddCommand command = new WallSettingAddCommand(
                "Sample Title",
                createMockMultipartFile("logo.png", "image/png")
        );

        Set<ConstraintViolation<WallSettingAddCommand>> violations = validator.validate(command);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void should_ThrowConstraintViolationException_when_EmptyTitle() {
        WallSettingAddCommand command = new WallSettingAddCommand(
                "",
                createMockMultipartFile("logo.png", "image/png")
        );

        Set<ConstraintViolation<WallSettingAddCommand>> violations = validator.validate(command);

        assertEquals(2, violations.size());

        assertFalse(violations.isEmpty());
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidLogo() {
        WallSettingAddCommand command = new WallSettingAddCommand(
                "Sample Title",
                createMockMultipartFile("logo.txt", "text/plain")
        );

        Set<ConstraintViolation<WallSettingAddCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<WallSettingAddCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals("{validation.constraints.MediaType.message}", violationMessage);
    }

    private MultipartFile createMockMultipartFile(String filename, String contentType) {
        try {
            return new MockMultipartFile(
                    "file",
                    filename,
                    contentType,
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(filename)
            );
        } catch (IOException e) {
            return null;
        }
    }
}

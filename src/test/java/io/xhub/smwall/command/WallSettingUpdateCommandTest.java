package io.xhub.smwall.command;

import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Set;

import static com.mongodb.assertions.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WallSettingUpdateCommandTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidTitle() {
        WallSettingUpdateCommand command = new WallSettingUpdateCommand(
                " ",
                new MockMultipartFile(
                        "logo1",
                        "logo1.png",
                        "image/png",
                        new byte[]{1, 2, 3, 4}
                )
        );

        Set<ConstraintViolation<WallSettingUpdateCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        assertFalse(violations.isEmpty());
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidLogo() {
        WallSettingAddCommand command = new WallSettingAddCommand(
                "Invalid logo 5",
                null
        );
        Set<ConstraintViolation<WallSettingAddCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        assertFalse(violations.isEmpty());
    }
}

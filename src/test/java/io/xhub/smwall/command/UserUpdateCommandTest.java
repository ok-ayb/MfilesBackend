package io.xhub.smwall.command;

import io.xhub.smwall.commands.UserUpdateCommand;
import io.xhub.smwall.constants.FormValidationCodes;
import io.xhub.smwall.constants.RoleName;
import io.xhub.smwall.domains.Authority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserUpdateCommandTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_not_throwAnyConstraintViolationException_when_validPayload() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(
                "123",
                RoleName.ROLE_ADMIN));

        UserUpdateCommand command = new UserUpdateCommand(
                "ValidFirstName",
                "ValidLastName",
                "valid-email@example.com",
                authorities
        );

        Set<ConstraintViolation<UserUpdateCommand>> violations = validator.validate(command);

        assertEquals(0, violations.size());
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidFirstName() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(
                "123",
                RoleName.ROLE_ADMIN));

        UserUpdateCommand command = new UserUpdateCommand(
                "@#$",
                "ValidLastName",
                "validEmail@example.com",
                authorities
        );

        Set<ConstraintViolation<UserUpdateCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<UserUpdateCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals(FormValidationCodes.INVALID_USER_FIRST_NAME_PATTERN, violationMessage);
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidLastName() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(
                "123",
                RoleName.ROLE_ADMIN));

        UserUpdateCommand command = new UserUpdateCommand(
                "ValidFirstName",
                "!@#",
                "valid-email@example.com",
                authorities
        );

        Set<ConstraintViolation<UserUpdateCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<UserUpdateCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals(FormValidationCodes.INVALID_USER_LAST_NAME_PATTERN, violationMessage);
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidEmail() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(
                "123",
                RoleName.ROLE_ADMIN));

        UserUpdateCommand command = new UserUpdateCommand(
                "ValidFirstName",
                "ValidLastName",
                "invalid-email",
                authorities
        );

        Set<ConstraintViolation<UserUpdateCommand>> violations = validator.validate(command);

        assertEquals(1, violations.size());

        ConstraintViolation<UserUpdateCommand> violation = violations.iterator().next();
        String violationMessage = violation.getMessage();

        assertEquals("must be a well-formed email address", violationMessage);
    }

    @Test
    public void should_ThrowConstraintViolationException_when_InvalidArgs() {
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority(
                "123",
                RoleName.ROLE_ADMIN));

        UserUpdateCommand command = new UserUpdateCommand(
                "",
                "",
                "",
                authorities
        );

        Set<ConstraintViolation<UserUpdateCommand>> violations = validator.validate(command);

        assertEquals(5, violations.size());

    }

}

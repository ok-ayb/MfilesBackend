package io.xhub.smwall.command;

import io.xhub.smwall.commands.LoginCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginCommandTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_NotThrowConstraintViolationException_When_EmailAndPasswordAreValid() {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("test@gmail.com");
        loginCommand.setPassword("Pa$word111");
        loginCommand.setRememberMe(true);

        Set<ConstraintViolation<LoginCommand>> violations = validator.validate(loginCommand);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void should_Throw_ConstraintViolationException_When_EmailIsInvalid() {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("invalid-email");
        loginCommand.setPassword("Pa$word111");
        loginCommand.setRememberMe(true);

        Set<ConstraintViolation<LoginCommand>> violations = validator.validate(loginCommand);

        assertEquals(1, violations.size());
        assertFalse(violations.isEmpty());

    }

    @Test
    public void should_ThrowConstraintViolationException_When_PasswordIsInvalid() {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("test@example.com");
        loginCommand.setPassword("invalid-password");
        loginCommand.setRememberMe(true);

        Set<ConstraintViolation<LoginCommand>> violations = validator.validate(loginCommand);

        assertEquals(1, violations.size());

        assertFalse(violations.isEmpty());
    }

    @Test
    public void should_Throw_ConstraintViolationException_When_EmailIsEmpty() {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("");
        loginCommand.setPassword("Abc123!@#");
        loginCommand.setRememberMe(true);

        Set<ConstraintViolation<LoginCommand>> violations = validator.validate(loginCommand);

        assertFalse(violations.isEmpty());

    }

    @Test
    public void should_Throw_ConstraintViolationException_When_PasswordIsEmpty() {
        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setEmail("test@example.com");
        loginCommand.setPassword("");
        loginCommand.setRememberMe(true);

        Set<ConstraintViolation<LoginCommand>> violations = validator.validate(loginCommand);

        assertFalse(violations.isEmpty());
    }
}

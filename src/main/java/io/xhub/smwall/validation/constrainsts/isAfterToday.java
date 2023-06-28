package io.xhub.smwall.validation.constrainsts;

import io.xhub.smwall.constants.FormValidationCodes;
import io.xhub.smwall.validation.validators.AfterTodayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {AfterTodayValidator.class})
public @interface isAfterToday {
    String message() default FormValidationCodes.DATE_AFTER_TODAY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] accept() default {};
}
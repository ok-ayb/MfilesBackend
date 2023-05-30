package io.xhub.smwall.validation.constrainsts;

import io.xhub.smwall.validation.validators.MediaTypeValidator;
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
@Constraint(validatedBy = {MediaTypeValidator.class})
public @interface MediaType {
    String message() default "{validation.constraints.MediaType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] accept() default {};
}
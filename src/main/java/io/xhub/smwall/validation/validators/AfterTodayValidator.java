package io.xhub.smwall.validation.validators;

import io.xhub.smwall.validation.constrainsts.isAfterToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.ZoneOffset;

public class AfterTodayValidator implements ConstraintValidator<isAfterToday, Instant> {
    private String[] acceptedContentTypes;

    @Override
    public void initialize(isAfterToday constraintAnnotation) {
        acceptedContentTypes = constraintAnnotation.accept();
    }

    @Override
    public boolean isValid(Instant value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Instant today = Instant.now();
        Instant date = value.atOffset(ZoneOffset.UTC).toInstant();

        return date.isAfter(today);
    }

}
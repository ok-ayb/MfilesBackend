package io.xhub.smwall.validation.validators;

import io.xhub.smwall.validation.constrainsts.MediaType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class MediaTypeValidator implements ConstraintValidator<MediaType, MultipartFile> {
    private String[] acceptedContentTypes;

    @Override
    public void initialize(MediaType constraintAnnotation) {
        acceptedContentTypes = constraintAnnotation.accept();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        String contentType = multipartFile.getContentType();
        return contentType != null && isValidContentType(contentType);
    }

    private boolean isValidContentType(String contentType) {
        return Arrays.stream(acceptedContentTypes)
                .anyMatch(contentType::equalsIgnoreCase);
    }
}
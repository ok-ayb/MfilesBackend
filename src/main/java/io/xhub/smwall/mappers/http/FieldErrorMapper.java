package io.xhub.smwall.mappers.http;

import io.xhub.smwall.dto.http.FieldErrorDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class FieldErrorMapper {
    public FieldErrorDTO toDTO(FieldError objectError) {
        if (objectError == null)
            return null;

        FieldErrorDTO fieldErrorDTO = new FieldErrorDTO();
        fieldErrorDTO.setField(objectError.getField());
        fieldErrorDTO.setMessage(objectError.getDefaultMessage());
        fieldErrorDTO.setCode(objectError.getCode());
        return fieldErrorDTO;
    }
}

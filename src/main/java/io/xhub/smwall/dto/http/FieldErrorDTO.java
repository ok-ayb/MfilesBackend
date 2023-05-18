package io.xhub.smwall.dto.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorDTO {
    private String field;
    private String message;
    private String code;
}

package io.xhub.smwall.exceptions;

import lombok.*;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ErrorMessage {
    private String key = null;
    private Integer code = null;
    private Object[] messageKeyParameters = null;
    private Map<String, ?> payload = null;

}

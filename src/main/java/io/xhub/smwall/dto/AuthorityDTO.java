package io.xhub.smwall.dto;

import io.xhub.smwall.constants.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDTO {
    private String id;

    private RoleName name;
}

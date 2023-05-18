package io.xhub.smwall.dto;

import io.xhub.smwall.domains.Authority;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private Set<Authority> authorities;
    private Instant createdAt;
    private Instant lastModifiedAt;
}

package io.xhub.smwall.commands;

import io.xhub.smwall.constants.RegexPatterns;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LoginCommand {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = RegexPatterns.USER_PASSWORD)
    private String password;

    private boolean rememberMe;
}

package io.xhub.smwall.commands;


import io.xhub.smwall.constants.FormValidationCodes;
import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.domains.Authority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class UserUpdateCommand {

    @NotBlank
    @Pattern(regexp = RegexPatterns.ALPHANUMERIC_DASH_APOS_SPACE, message = FormValidationCodes.INVALID_USER_FIRST_NAME_PATTERN)
    private final String firstName;

    @NotBlank
    @Pattern(regexp = RegexPatterns.ALPHANUMERIC_DASH_APOS_SPACE, message = FormValidationCodes.INVALID_USER_LAST_NAME_PATTERN)
    private final String lastName;

    @NotBlank
    @Email
    private final String email;

    private final Boolean activated;

    private final Set<Authority> authorities;
}

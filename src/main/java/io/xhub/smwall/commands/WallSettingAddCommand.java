package io.xhub.smwall.commands;

import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.validation.constrainsts.MediaType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class WallSettingAddCommand {
    @NotBlank
    @Pattern(regexp = RegexPatterns.WALL_TITLE)
    private String title;

    @NotNull
    @MediaType(accept = {"image/png", "image/jpg", "image/jpeg", "image/svg+xml"})
    private MultipartFile logo;
}

package io.xhub.smwall.commands;

import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.validation.constrainsts.MediaType;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class WallSettingUpdateCommand {
    @Pattern(regexp = RegexPatterns.WALL_TITLE)
    private final String title;

    @MediaType(accept = {"image/png", "image/jpg", "image/jpeg", "image/svg+xml"})
    private final MultipartFile logo;
}

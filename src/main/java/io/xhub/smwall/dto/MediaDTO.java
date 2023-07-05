package io.xhub.smwall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.enumeration.MediaType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MediaDTO {
    private String id;

    private String text;

    private MediaType type;

    private MediaSource source;

    private String url;

    private String thumbnail;

    private String permalink;

    private List<MediaChildDTO> children;

    private Instant timestamp;

    private OwnerDTO owner;

    private Boolean hidden = false;

    private Boolean pinned = false;

    private List<String> sourceTypes = new ArrayList<>();

    private Boolean clean = false;

    private Boolean analyzed = false;

    @JsonProperty("textContainsOnlyHashtags")
    private boolean textContainsOnlyHashtags() {
        return this.hasText() && this.getText()
                .replaceAll(RegexPatterns.SOCIAL_MEDIA_HASHTAG, "")
                .isBlank();
    }

    private boolean hasText() {
        return this.getText() != null && !this.getText().isBlank();
    }

}

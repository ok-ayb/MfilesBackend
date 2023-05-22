package io.xhub.smwall.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.xhub.smwall.constants.RegexPatterns;
import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.enumeration.MediaType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class MediaDTO {
    private String id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;

    private MediaType type;

    private MediaSource source;

    private String url;

    private String permalink;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MediaChildDTO> children;

    private Instant timestamp;

    private OwnerDTO owner;

    private boolean pinned;

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

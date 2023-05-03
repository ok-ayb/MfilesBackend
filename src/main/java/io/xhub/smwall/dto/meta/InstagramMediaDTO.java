package io.xhub.smwall.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class InstagramMediaDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("media_type")
    private String type;

    @JsonProperty("media_url")
    private String url;

    @JsonProperty("permalink")
    private String permalink;

    @JsonProperty("timestamp")
    private Instant timestamp;

    @JsonProperty("owner")
    private InstagramUserDTO owner;

    @JsonProperty("children")
    private InstagramMediaChildrenDTO children;
}

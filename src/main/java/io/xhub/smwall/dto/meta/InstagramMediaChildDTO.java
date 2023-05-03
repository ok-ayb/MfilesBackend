package io.xhub.smwall.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstagramMediaChildDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("media_url")
    private String url;

    @JsonProperty("media_type")
    private String type;
}
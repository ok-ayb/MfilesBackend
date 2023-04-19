package io.xhub.smwall.smbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class IGMedia {
    @JsonProperty("id")
    private String id;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("media_url")
    private String mediaUrl;

    @JsonProperty("permalink")
    private String permalink;

    @JsonProperty("timestamp")
    private Instant timestamp;
}

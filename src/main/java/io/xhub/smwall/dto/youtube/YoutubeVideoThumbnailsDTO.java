package io.xhub.smwall.dto.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YoutubeVideoThumbnailsDTO {
    @JsonProperty("default")
    private YoutubeVideoThumbnailDTO defaultValue;

    private YoutubeVideoThumbnailDTO medium;

    private YoutubeVideoThumbnailDTO high;
}

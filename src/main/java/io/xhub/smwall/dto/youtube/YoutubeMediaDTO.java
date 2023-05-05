package io.xhub.smwall.dto.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YoutubeMediaDTO {
    @JsonProperty("id")
    private YoutubeVideoIdDTO id;
    private YoutubeVideoSnippetDTO snippet;

}

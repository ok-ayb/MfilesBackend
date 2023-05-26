package io.xhub.smwall.dto.youtube;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class YoutubeMediaDTO {
    @JsonProperty("id")
    private YoutubeVideoIdDTO id;
    private YoutubeVideoSnippetDTO snippet;

    private List<String> sourceTypes = new ArrayList<>();

}

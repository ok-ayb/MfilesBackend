package io.xhub.smwall.client.response;

import io.xhub.smwall.dto.youtube.YoutubeMediaDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class YoutubeMediaResponse {

    private List<YoutubeMediaDTO> items;

}

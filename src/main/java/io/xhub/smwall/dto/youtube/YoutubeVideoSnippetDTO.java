package io.xhub.smwall.dto.youtube;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class YoutubeVideoSnippetDTO {
    private Instant publishedAt;
    private String channelId;
    private String title;
    private String channelTitle;
    private String avatar;
}

package io.xhub.smwall.mappers.youtube;

import io.xhub.smwall.config.YoutubeProperties;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.dto.youtube.YoutubeMediaDTO;
import io.xhub.smwall.dto.youtube.YoutubeVideoIdDTO;
import io.xhub.smwall.dto.youtube.YoutubeVideoSnippetDTO;
import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.enumeration.MediaType;
import io.xhub.smwall.utlis.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YoutubeMediaMapper {
    private final YoutubeProperties youtubeProperties;
    private final YoutubeSnippetMapper youtubeSnippetMapper;

    public Media toEntity(YoutubeMediaDTO youtubeMediaDTO) {
        if (youtubeMediaDTO == null)
            return null;

        Media media = new Media();
        YoutubeVideoIdDTO videoIdDTO = youtubeMediaDTO.getId();
        YoutubeVideoSnippetDTO videoSnippetDTO = youtubeMediaDTO.getSnippet();

        media.setId(StringUtils.concat(youtubeProperties.getResourceIdPrefix(), videoIdDTO.getVideoId()));
        media.setText(youtubeMediaDTO.getSnippet().getTitle());
        media.setType(MediaType.VIDEO);
        media.setSource(MediaSource.YOUTUBE);
        media.setUrl(StringUtils.generateStringEmbedUrl(videoIdDTO.getVideoId()));
        media.setTimestamp(videoSnippetDTO.getPublishedAt());
        media.setOwner(this.youtubeSnippetMapper.toOwnerEntity(videoSnippetDTO));

        return media;
    }
}
package io.xhub.smwall.mappers.youtube;

import io.xhub.smwall.config.YoutubeProperties;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.dto.youtube.YoutubeMediaDTO;
import io.xhub.smwall.dto.youtube.YoutubeVideoIdDTO;
import io.xhub.smwall.dto.youtube.YoutubeVideoSnippetDTO;
import io.xhub.smwall.dto.youtube.YoutubeVideoThumbnailsDTO;
import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.enumeration.MediaType;
import io.xhub.smwall.utlis.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        YoutubeVideoThumbnailsDTO thumbnailsDTO = videoSnippetDTO.getThumbnails();

        String videoId = videoIdDTO.getVideoId();
        String videoTitle = videoSnippetDTO.getTitle();

        media.setId(StringUtils.concat(youtubeProperties.getResourceIdPrefix(), videoId));
        media.setText(videoTitle);
        media.setType(MediaType.VIDEO);
        media.setSource(MediaSource.YOUTUBE);
        media.setUrl(StringUtils.generateStringEmbedUrl(videoId));
        media.setPermalink(StringUtils.getYouTubeVideoUrlFromId(videoId));
        media.setTimestamp(videoSnippetDTO.getPublishedAt());
        media.setOwner(this.youtubeSnippetMapper.toOwnerEntity(videoSnippetDTO));
        media.setSourceTypes(youtubeMediaDTO.getSourceTypes());

        media.setThumbnail(
                thumbnailsDTO.getHigh() != null ? thumbnailsDTO.getHigh().getUrl() :
                        thumbnailsDTO.getMedium() != null ? thumbnailsDTO.getMedium().getUrl() :
                                thumbnailsDTO.getDefaultValue().getUrl()
        );

        return media;
    }

    public List<Media> toEntity(List<YoutubeMediaDTO> mediaDTOS) {
        return mediaDTOS.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
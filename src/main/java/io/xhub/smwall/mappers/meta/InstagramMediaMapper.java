package io.xhub.smwall.mappers.meta;

import io.xhub.smwall.config.MetaProperties;
import io.xhub.smwall.domains.Media;
import io.xhub.smwall.dto.meta.InstagramMediaDTO;
import io.xhub.smwall.enumeration.MediaSource;
import io.xhub.smwall.enumeration.MediaType;
import io.xhub.smwall.utlis.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InstagramMediaMapper {
    private final MetaProperties metaProperties;
    private final InstagramUserMapper instagramUserMapper;
    private final InstagramMediaChildMapper instagramMediaChildMapper;

    public Media toEntity(InstagramMediaDTO instagramMediaDTO) {
        if (instagramMediaDTO == null)
            return null;

        Media media = new Media();
        media.setOwner(this.instagramUserMapper.toOwnerEntity(instagramMediaDTO.getOwner()));
        media.setText(instagramMediaDTO.getCaption());
        media.setId(StringUtils.concat(metaProperties.getResourceIdPrefix(), instagramMediaDTO.getId()));

        if (instagramMediaDTO.getType() != null) {
            media.setType(Enum.valueOf(MediaType.class, instagramMediaDTO.getType()));
        }

        media.setUrl(instagramMediaDTO.getUrl());
        media.setThumbnail(instagramMediaDTO.getThumbnailUrl());
        media.setPermalink(instagramMediaDTO.getPermalink());

        if (instagramMediaDTO.getChildren() != null) {
            media.setChildren(instagramMediaDTO.getChildren()
                    .getData()
                    .stream()
                    .map(this.instagramMediaChildMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        media.setTimestamp(instagramMediaDTO.getTimestamp());
        media.setSource(MediaSource.INSTAGRAM);
        media.setSourceTypes(instagramMediaDTO.getSourceTypes());
        return media;
    }
}
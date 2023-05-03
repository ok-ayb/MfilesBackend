package io.xhub.smwall.mappers.meta;

import io.xhub.smwall.domains.MediaChild;
import io.xhub.smwall.dto.meta.InstagramMediaChildDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramMediaChildMapper {
    public MediaChild toEntity(InstagramMediaChildDTO instagramMediaChildDTO) {
        if (instagramMediaChildDTO == null)
            return null;

        MediaChild mediaChild = new MediaChild();
        mediaChild.setId(instagramMediaChildDTO.getId());
        mediaChild.setUrl(instagramMediaChildDTO.getUrl());
        mediaChild.setType(instagramMediaChildDTO.getType());
        return mediaChild;
    }
}
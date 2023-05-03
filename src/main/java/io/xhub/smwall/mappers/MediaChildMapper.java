package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.MediaChild;
import io.xhub.smwall.dto.MediaChildDTO;
import org.springframework.stereotype.Component;

@Component
public class MediaChildMapper {
    public MediaChildDTO toDTO(MediaChild mediaChild) {
        if (mediaChild == null)
            return null;

        MediaChildDTO mediaChildDTO = new MediaChildDTO();
        mediaChildDTO.setId(mediaChild.getId());
        mediaChildDTO.setUrl(mediaChild.getUrl());
        mediaChildDTO.setType(mediaChild.getType());
        return mediaChildDTO;
    }
}

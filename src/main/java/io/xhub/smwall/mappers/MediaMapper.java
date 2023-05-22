package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.Media;
import io.xhub.smwall.domains.MediaChild;
import io.xhub.smwall.dto.MediaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MediaMapper {
    private final OwnerMapper ownerMapper;
    private final MediaChildMapper mediaChildMapper;

    public MediaDTO toDTO(Media media) {
        if (media == null)
            return null;

        MediaDTO mediaDTO = new MediaDTO();
        mediaDTO.setId(media.getId());
        mediaDTO.setText(media.getText());
        mediaDTO.setType(media.getType());
        mediaDTO.setSource(media.getSource());
        mediaDTO.setUrl(media.getUrl());
        mediaDTO.setPermalink(media.getPermalink());
        mediaDTO.setPinned(media.isPinned());
        mediaDTO.setHidden(media.isHidden());

        List<MediaChild> children = media.getChildren();
        if (children != null) {
            mediaDTO.setChildren(
                    media.getChildren()
                            .stream()
                            .map(mediaChildMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        mediaDTO.setTimestamp(media.getTimestamp());
        mediaDTO.setOwner(ownerMapper.toDTO(media.getOwner()));
        return mediaDTO;
    }
}

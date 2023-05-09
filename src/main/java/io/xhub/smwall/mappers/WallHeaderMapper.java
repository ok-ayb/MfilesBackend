package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.WallHeader;
import io.xhub.smwall.dto.WallHeaderDTO;
import org.springframework.stereotype.Component;

@Component
public class WallHeaderMapper {
    public WallHeaderDTO toDTO(WallHeader wallHeader) {
        if (wallHeader == null) return null;
        WallHeaderDTO wallHeaderDTO = new WallHeaderDTO();
        wallHeaderDTO.setLogoUrl(wallHeader.getLogoUrl());
        wallHeaderDTO.setMention(wallHeader.getMention());
        wallHeaderDTO.setSources(wallHeader.getSources());
        wallHeaderDTO.setHashtags(wallHeader.getHashtags());
        return wallHeaderDTO;
    }
}

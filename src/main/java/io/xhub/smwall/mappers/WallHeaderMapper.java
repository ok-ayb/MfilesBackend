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
        wallHeaderDTO.setTitle(wallHeader.getTitle());
        return wallHeaderDTO;
    }
}

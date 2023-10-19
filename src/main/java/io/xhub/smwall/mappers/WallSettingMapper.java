package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.BinaryImage;
import io.xhub.smwall.domains.WallSetting;
import io.xhub.smwall.dto.WallSettingDTO;
import io.xhub.smwall.utlis.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class WallSettingMapper {
    public WallSettingDTO toDTO(WallSetting wallSetting) {
        if (wallSetting == null)
            return null;

        BinaryImage logo = wallSetting.getLogo();

        WallSettingDTO wallSettingDTO = new WallSettingDTO();
        wallSettingDTO.setId(wallSetting.getId());
        wallSettingDTO.setTitle(wallSetting.getTitle());
        wallSettingDTO.setFilename(logo.getFilename());

        String logoBase64Data = Base64.getEncoder().encodeToString(logo.getValue().getData());
        wallSettingDTO.setLogoBase64(StringUtils.getBase64Data(logo.getContentType(), logoBase64Data));

        return wallSettingDTO;
        Itirator itirator = lis
    }
}

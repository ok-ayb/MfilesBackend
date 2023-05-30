package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.commands.AddWallSettingCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.dto.WallSettingDTO;
import io.xhub.smwall.mappers.WallSettingMapper;
import io.xhub.smwall.service.WallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Wall Management Resource")
@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.WALL)
@RequiredArgsConstructor
public class WallController {
    private final WallService wallService;
    private final WallSettingMapper wallSettingMapper;

    @ApiOperation(value = "Add a wall setting")
    @PostMapping(ApiPaths.SETTINGS)
    public ResponseEntity<WallSettingDTO> addWallSetting(@Valid @ModelAttribute AddWallSettingCommand addWallSettingCommand) {
        return ResponseEntity.ok()
                .body(wallSettingMapper.toDTO(wallService.addWallSetting(addWallSettingCommand)));
    }
}

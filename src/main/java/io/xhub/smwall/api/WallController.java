package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.commands.WallSettingUpdateCommand;
import io.xhub.smwall.commands.WallSettingAddCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.dto.WallSettingDTO;
import io.xhub.smwall.mappers.WallSettingMapper;
import io.xhub.smwall.service.WallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Wall Management Resource")
@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.WALL)
@RequiredArgsConstructor
public class WallController {
    private final WallService wallService;
    private final WallSettingMapper wallSettingMapper;

    @ApiOperation(value = "Add a wall setting")
    @PostMapping(ApiPaths.SETTINGS)
    public ResponseEntity<WallSettingDTO> addWallSetting(@Valid @ModelAttribute WallSettingAddCommand wallSettingAddCommand) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(wallSettingMapper.toDTO(wallService.addWallSetting(wallSettingAddCommand)));
    }

    @ApiOperation(value = "Partially update a wall setting")
    @PatchMapping(ApiPaths.SETTINGS + "/{id}")
    public ResponseEntity<WallSettingDTO> updateWallSetting(
            @Valid @ModelAttribute WallSettingUpdateCommand wallSettingUpdateCommand,
            @PathVariable String id
    ) {
        return ResponseEntity.ok()
                .body(wallSettingMapper.toDTO(wallService.updateWallSetting(id, wallSettingUpdateCommand)));
    }
}

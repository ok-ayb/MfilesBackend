package io.xhub.smwall.api;

import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.dto.WallHeaderDTO;
import io.xhub.smwall.holders.ApiPaths;
import io.xhub.smwall.mappers.WallHeaderMapper;
import io.xhub.smwall.service.WallHeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.HEADER)
@RequiredArgsConstructor
public class WallHeaderController {
    private final WallHeaderService wallHeaderService;
    private final WallHeaderMapper wallHeaderMapper;


    @GetMapping
    @ApiOperation(value = "Get wall header info")
    public ResponseEntity<WallHeaderDTO> getWallHeaderInfo() {
        return ResponseEntity.ok(wallHeaderMapper.toDTO(wallHeaderService.getWallHeaderInfo()));
    }
}

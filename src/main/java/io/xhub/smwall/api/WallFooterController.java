package io.xhub.smwall.api;

import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.dto.WallFooterDTO;
import io.xhub.smwall.holders.ApiPaths;
import io.xhub.smwall.mappers.WallFooterMapper;
import io.xhub.smwall.service.WallFooterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.FOOTER)
@RequiredArgsConstructor
public class WallFooterController {
    private final WallFooterService wallFooterService;
    private final WallFooterMapper wallFooterMapper;

    @GetMapping
    @ApiOperation(value = "Get wall footer info")
    public ResponseEntity<WallFooterDTO> getWallFooterInfo() {
        return ResponseEntity.ok(wallFooterMapper.toDTO(wallFooterService.getWallFooterInfo()));
    }
}

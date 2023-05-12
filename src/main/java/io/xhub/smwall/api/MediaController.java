package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.dto.MediaDTO;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.mappers.MediaMapper;
import io.xhub.smwall.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Media Management Resource")
@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.MEDIA)
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;
    private final MediaMapper mediaMapper;

    @ApiOperation(value = "List of media")
    @GetMapping
    public ResponseEntity<Page<MediaDTO>> getAllMedia(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(mediaService.getAllMedia(pageable).map(mediaMapper::toDTO));
    }
}

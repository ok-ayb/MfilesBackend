package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.dto.AnnouncementDTO;
import io.xhub.smwall.holders.ApiPaths;
import io.xhub.smwall.mappers.AnnouncementMapper;
import io.xhub.smwall.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Announcement Management Resource")
@RestController
@RequestMapping(ApiPaths.V1 + ApiPaths.ANNOUNCEMENTS)
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final AnnouncementMapper announcementMapper;

    @ApiOperation(value = "Current announcement")
    @GetMapping("/current")
    public ResponseEntity<AnnouncementDTO> getCurrentAnnouncement() {
        return ResponseEntity.ok(announcementMapper.toDTO(announcementService.getCurrentAnnouncement()));
    }
}

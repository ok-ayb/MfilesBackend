package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.dto.AnnouncementDTO;
import io.xhub.smwall.mappers.AnnouncementMapper;
import io.xhub.smwall.service.AnnouncementService;
import io.xhub.smwall.service.query.AnnouncementQuery;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @ApiOperation(value = "List of announcements")
    @GetMapping
    public ResponseEntity<Page<AnnouncementDTO>> getAllAnnouncements(AnnouncementQuery announcementQuery, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(
                announcementService.getAllAnnouncement(announcementQuery.buildPredicate(), pageable)
                        .map(announcementMapper::toDTO)
        );
    }
}

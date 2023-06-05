package io.xhub.smwall.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.xhub.smwall.commands.AnnouncementCommand;
import io.xhub.smwall.constants.ApiPaths;
import io.xhub.smwall.dto.AnnouncementDTO;
import io.xhub.smwall.mappers.AnnouncementMapper;
import io.xhub.smwall.service.AnnouncementService;
import io.xhub.smwall.service.query.AnnouncementQuery;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Delete an announcement by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable String id) {
        announcementService.deleteAnnouncementById(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Create a new announcement")
    @PostMapping()
    public ResponseEntity<AnnouncementDTO> addAnnouncement(@RequestBody AnnouncementCommand announcementCommand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(announcementMapper.toDTO(announcementService.addAnnouncement(announcementCommand)));
    }


    @ApiOperation(value = "Update an announcement")
    @PatchMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(@PathVariable String id, @RequestBody AnnouncementCommand announcementCommand) {
        return ResponseEntity.ok()
                .body(announcementMapper.toDTO(announcementService.updateAnnouncement(id, announcementCommand)));
    }
}

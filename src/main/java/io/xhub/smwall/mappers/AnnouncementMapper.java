package io.xhub.smwall.mappers;

import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.dto.AnnouncementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnnouncementMapper {
    public AnnouncementDTO toDTO(Announcement announcement) {
        if (announcement == null)
            return null;

        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement.getId());
        announcementDTO.setTitle(announcement.getTitle());
        announcementDTO.setDescription(announcement.getDescription());
        announcementDTO.setStartDate(announcement.getStartDate());
        announcementDTO.setEndDate(announcement.getEndDate());
        announcementDTO.setDeleted(announcement.isDeleted());

        return announcementDTO;
    }
}

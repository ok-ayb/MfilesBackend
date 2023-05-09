package io.xhub.smwall.service;

import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.holders.ApiClientErrorCodes;
import io.xhub.smwall.repositories.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public Announcement getCurrentAnnouncement() {
        log.info("Start getting current announcement");
        return announcementRepository
                .findFirstByEndDateGreaterThanEqualOrderByStartDateAsc(LocalDateTime.now())
                .orElseThrow(() -> new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_NOT_FOUND.getErrorMessage()));
    }

}

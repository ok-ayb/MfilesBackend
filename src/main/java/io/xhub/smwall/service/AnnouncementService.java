package io.xhub.smwall.service;

import com.querydsl.core.types.Predicate;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.repositories.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public Page<Announcement> getAllAnnouncement(Predicate predicate, Pageable pageable) {
        log.info("Start getting all announcements");
        if (predicate == null) {
            return announcementRepository.findAll(pageable);
        }
        return announcementRepository.findAll(predicate, pageable);
    }

}

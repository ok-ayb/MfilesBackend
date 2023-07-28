package io.xhub.smwall.service;

import com.querydsl.core.types.Predicate;
import io.xhub.smwall.commands.AnnouncementAddCommand;
import io.xhub.smwall.commands.AnnouncementUpdateCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.events.announcement.AnnouncementCreatedEvent;
import io.xhub.smwall.events.announcement.AnnouncementDeletedEvent;
import io.xhub.smwall.events.announcement.AnnouncementUpdatedEvent;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.function.BiPredicate;

import static io.xhub.smwall.utlis.AssertUtils.assertIsAfterDate;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {
    private final ApplicationEventPublisher eventPublisher;

    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public Page<Announcement> getAllAnnouncement(Predicate predicate, Pageable pageable) {
        log.info("Start getting all announcements");
        if (predicate == null) {
            return announcementRepository.findAll(pageable);
        }
        return announcementRepository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    public Announcement getAnnouncementById(String id) {
        log.info("Start getting announcement by id");
        return announcementRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_NOT_FOUND.getErrorMessage()));
    }

    public void deleteAnnouncementById(String id) {
        log.info("Start deleting announcement with ID: {}", id);
        Announcement announcement = getAnnouncementById(id);
        announcementRepository.delete(announcement);
        eventPublisher.publishEvent(new AnnouncementDeletedEvent(this, announcement));
    }

    public Announcement addAnnouncement(final AnnouncementAddCommand announcementAddCommand) {
        log.info("Start creating an announcement");
        Announcement announcement = announcementRepository.save(Announcement.create(thereAnyAnnouncement(), announcementAddCommand));
        eventPublisher.publishEvent(new AnnouncementCreatedEvent(this, announcement));
        return announcement;
    }

    private BiPredicate<Instant, Instant> thereAnyAnnouncement() {
        return (startDate, endDate) -> {
            log.info("Existence check for announcements");
            return announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(endDate, startDate);
        };
    }

    public Announcement updateAnnouncement(String id, AnnouncementUpdateCommand announcementUpdateCommand) {
        log.info("Start updating announcement ");
        Announcement announcement = getAnnouncementById(id);

        if (announcementUpdateCommand.getStartDate() != null) {
            assertIsAfterDate(announcementUpdateCommand.getStartDate(), announcementUpdateCommand.getEndDate());
        } else {
            assertIsAfterDate(announcement.getStartDate(), announcementUpdateCommand.getEndDate());
        }

        announcement.update(this::existInBetween, announcementUpdateCommand);
        announcementRepository.save(announcement);
        eventPublisher.publishEvent(new AnnouncementUpdatedEvent(this, announcement));

        return announcement;
    }

    private void existInBetween(Instant endDate, Instant startDate, String id) {
        if (announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdIsNot(endDate, startDate, id)) {
            throw new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_ALREADY_EXISTS.getErrorMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public Announcement getClosestAnnouncement() {
        return announcementRepository.findFirstByEndDateAfterOrderByStartDateAsc(Instant.now())
                .orElse(null);
    }
}

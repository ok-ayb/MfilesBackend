package io.xhub.smwall.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import io.xhub.smwall.commands.AnnouncementCommand;
import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.domains.Announcement;
import io.xhub.smwall.domains.QAnnouncement;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.function.BiPredicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final WebSocketService webSocketService;

    public Page<Announcement> getAllAnnouncement(Predicate basePredicate, Pageable pageable) {
        log.info("Start getting all announcements");
        Predicate deletedFilter = QAnnouncement.announcement.deleted.eq(false);
        Predicate finalPredicate = basePredicate != null ? ExpressionUtils
                .allOf(basePredicate, deletedFilter) : deletedFilter;
        assert finalPredicate != null;
        return announcementRepository.findAll(finalPredicate, pageable);
    }

    public Announcement getAnnouncementById(String id) {
        log.info("Start getting announcement by id '{}'", id);
        return announcementRepository
                .findById(id)
                .orElseThrow(() ->
                        new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_NOT_FOUND.getErrorMessage()));
    }

    public void deleteAnnouncementById(String id) {
        log.info("Start deleting announcement with ID: {}", id);
        try {
            Announcement announcement = getAnnouncementById(id);
            announcement.delete();
            announcementRepository.save(announcement);
        } catch (Exception e) {
            throw new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_NOT_FOUND.getErrorMessage());
        }
    }

    public Announcement addAnnouncement(final AnnouncementCommand announcementCommand) {
        announcementCommand.validate();
        log.info("Start creating an announcement");
        Announcement announcement = announcementRepository.save(Announcement.create(thereAnyAnnouncement(), announcementCommand));
        log.info("Announcement created: {}", announcement);
        webSocketService.sendNewAnnouncement(announcement);
        return announcement;
    }

    private BiPredicate<Instant, Instant> thereAnyAnnouncement() {
        BiPredicate<Instant, Instant> biPredicate = (startDate, endDate) -> {
            log.info("Existence check for announcements");
            return announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedFalse(endDate, startDate);
        };
        return biPredicate;
    }

    public Announcement updateAnnouncement(String id, AnnouncementCommand announcementCommand) {
        log.info("Start updating announcement ");
        announcementCommand.validate();

        Announcement announcement = getAnnouncementById(id);

        announcement.update(this::existInBetween, announcementCommand);
        announcementRepository.save(announcement);
        webSocketService.sendUpdatedAnnouncement(announcement);

        return announcement;
    }

    private void existInBetween(Instant endDate, Instant startDate, String id) {
        if (announcementRepository.existsByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdIsNotAndDeletedFalse(endDate, startDate, id)) {
            throw new BusinessException(ApiClientErrorCodes.ANNOUNCEMENT_ALREADY_EXISTS.getErrorMessage());
        }
    }

}

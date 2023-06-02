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
        BiPredicate<Instant, Instant> ThereAnyAnnouncement = (startDate, endDate) -> {
            if (announcementRepository.existsByStartDateBetweenAndDeletedFalse(startDate, endDate) || announcementRepository.existsByEndDateBetweenAndDeletedFalse(startDate, endDate) || announcementRepository.existsByStartDateBeforeAndEndDateAfterAndDeletedFalse(startDate, endDate))
                return true;
            return false;
        };

        return announcementRepository.save(Announcement.create(ThereAnyAnnouncement, announcementCommand));
    }
}


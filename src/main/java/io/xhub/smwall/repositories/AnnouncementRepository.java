package io.xhub.smwall.repositories;

import com.querydsl.core.types.Predicate;
import io.xhub.smwall.domains.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String>, QuerydslPredicateExecutor<Announcement> {
    Optional<Announcement> findByIdAndDeletedFalse(String id);
    boolean existsByStartDateLessThanEqualAndEndDateGreaterThanEqualAndDeletedFalse(Instant endDate, Instant startDate);
    boolean existsByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdIsNotAndDeletedFalse(Instant endDate, Instant startDate, String id);
    Page<Announcement> findAllByDeletedFalseOrderByCreatedAtDesc(Predicate predicate, Pageable pageable);
    Optional<Announcement> findFirstByEndDateAfterAndDeletedFalseOrderByStartDateAsc(Instant date);

}
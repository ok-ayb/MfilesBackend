package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String>, QuerydslPredicateExecutor<Announcement> {
    boolean existsByStartDateLessThanEqualAndEndDateGreaterThanEqual(Instant endDate, Instant startDate);

    boolean existsByStartDateLessThanEqualAndEndDateGreaterThanEqualAndIdIsNot(Instant endDate, Instant startDate, String id);

    Optional<Announcement> findFirstByEndDateAfterOrderByStartDateAsc(Instant date);
}
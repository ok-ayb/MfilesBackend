package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Announcement;
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

}
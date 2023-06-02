package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;


@Repository
public interface AnnouncementRepository extends MongoRepository<Announcement, String>, QuerydslPredicateExecutor<Announcement> {
        boolean existsByStartDateBetweenAndDeletedFalse(Instant newStartDate, Instant newEndDate);

        boolean existsByEndDateBetweenAndDeletedFalse(Instant newStartDate, Instant newEndDate);

        boolean existsByStartDateBeforeAndEndDateAfterAndDeletedFalse(Instant newStartDate, Instant newEndDate);
}
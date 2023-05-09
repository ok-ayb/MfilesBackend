package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AnnouncementRepository extends MongoRepository<Announcement, String> {

    Optional<Announcement> findFirstByEndDateGreaterThanEqualOrderByStartDateAsc(LocalDateTime endDate);

}

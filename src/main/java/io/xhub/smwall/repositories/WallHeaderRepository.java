package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.WallHeader;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository


public interface WallHeaderRepository extends MongoRepository<WallHeader, String> {
    Optional<WallHeader> findFirstByIdIsNotNullOrderByTimestampDesc();
}

package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends MongoRepository<Media, String>, QuerydslPredicateExecutor<Media> {
    Optional<Media> findByPinned(boolean pinned);
}

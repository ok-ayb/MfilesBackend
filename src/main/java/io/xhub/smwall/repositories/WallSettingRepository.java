package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.WallSetting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WallSettingRepository extends MongoRepository<WallSetting, String> {
    Optional<WallSetting> findFirstByOrderByCreatedAtDesc();
}

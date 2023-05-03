package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Media;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaRepository extends MongoRepository<Media, String>{
}

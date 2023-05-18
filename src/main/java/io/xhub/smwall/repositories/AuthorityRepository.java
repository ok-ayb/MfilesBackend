package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Authority;
import io.xhub.smwall.domains.Media;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}

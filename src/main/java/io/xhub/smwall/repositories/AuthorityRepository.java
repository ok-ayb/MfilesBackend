package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {
}

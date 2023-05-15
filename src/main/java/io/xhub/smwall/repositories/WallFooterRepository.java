package io.xhub.smwall.repositories;

import io.xhub.smwall.domains.WallFooter;
import io.xhub.smwall.exceptions.BusinessException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WallFooterRepository extends MongoRepository<WallFooter, String> {
    Optional<WallFooter> findFirstByIdIsNotNullOrderByTimestampDesc() throws BusinessException;
}

package rim.mr.covtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import rim.mr.covtest.domain.Authority;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {}

package rim.mr.covtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rim.mr.covtest.domain.TestCovid;

/**
 * Spring Data MongoDB repository for the TestCovid entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestCovidRepository extends MongoRepository<TestCovid, String> {}

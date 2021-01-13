package rim.mr.covtest.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rim.mr.covtest.domain.Personne;

/**
 * Spring Data MongoDB repository for the Personne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonneRepository extends MongoRepository<Personne, String> {}

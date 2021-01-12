package rim.mr.covtest.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rim.mr.covtest.domain.Personne;

/**
 * Spring Data Elasticsearch repository for the {@link Personne} entity.
 */
public interface PersonneSearchRepository extends ElasticsearchRepository<Personne, String> {}

package rim.mr.covtest.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rim.mr.covtest.domain.TestCovid;

/**
 * Spring Data Elasticsearch repository for the {@link TestCovid} entity.
 */
public interface TestCovidSearchRepository extends ElasticsearchRepository<TestCovid, String> {}

package rim.mr.covtest.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import rim.mr.covtest.domain.User;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, String> {}

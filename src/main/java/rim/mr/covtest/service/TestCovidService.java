package rim.mr.covtest.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rim.mr.covtest.service.dto.TestCovidDTO;

/**
 * Service Interface for managing {@link rim.mr.covtest.domain.TestCovid}.
 */
public interface TestCovidService {
    /**
     * Save a testCovid.
     *
     * @param testCovidDTO the entity to save.
     * @return the persisted entity.
     */
    TestCovidDTO save(TestCovidDTO testCovidDTO);

    /**
     * Get all the testCovids.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestCovidDTO> findAll(Pageable pageable);

    /**
     * Get the "id" testCovid.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TestCovidDTO> findOne(String id);

    /**
     * Delete the "id" testCovid.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the testCovid corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TestCovidDTO> search(String query, Pageable pageable);
}

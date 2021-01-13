package rim.mr.covtest.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rim.mr.covtest.service.dto.PersonneDTO;

/**
 * Service Interface for managing {@link rim.mr.covtest.domain.Personne}.
 */
public interface PersonneService {
    /**
     * Save a personne.
     *
     * @param personneDTO the entity to save.
     * @return the persisted entity.
     */
    PersonneDTO save(PersonneDTO personneDTO);

    /**
     * Get all the personnes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonneDTO> findOne(String id);

    /**
     * Delete the "id" personne.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the personne corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PersonneDTO> search(String query, Pageable pageable);
}

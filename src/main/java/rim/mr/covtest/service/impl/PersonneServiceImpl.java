package rim.mr.covtest.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rim.mr.covtest.domain.Personne;
import rim.mr.covtest.repository.PersonneRepository;
import rim.mr.covtest.repository.search.PersonneSearchRepository;
import rim.mr.covtest.service.PersonneService;
import rim.mr.covtest.service.dto.PersonneDTO;
import rim.mr.covtest.service.mapper.PersonneMapper;

/**
 * Service Implementation for managing {@link Personne}.
 */
@Service
public class PersonneServiceImpl implements PersonneService {
    private final Logger log = LoggerFactory.getLogger(PersonneServiceImpl.class);

    private final PersonneRepository personneRepository;

    private final PersonneMapper personneMapper;

    private final PersonneSearchRepository personneSearchRepository;

    public PersonneServiceImpl(
        PersonneRepository personneRepository,
        PersonneMapper personneMapper,
        PersonneSearchRepository personneSearchRepository
    ) {
        this.personneRepository = personneRepository;
        this.personneMapper = personneMapper;
        this.personneSearchRepository = personneSearchRepository;
    }

    @Override
    public PersonneDTO save(PersonneDTO personneDTO) {
        log.debug("Request to save Personne : {}", personneDTO);
        Personne personne = personneMapper.toEntity(personneDTO);
        personne = personneRepository.save(personne);
        PersonneDTO result = personneMapper.toDto(personne);
        personneSearchRepository.save(personne);
        return result;
    }

    @Override
    public Page<PersonneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Personnes");
        return personneRepository.findAll(pageable).map(personneMapper::toDto);
    }

    @Override
    public Optional<PersonneDTO> findOne(String id) {
        log.debug("Request to get Personne : {}", id);
        return personneRepository.findById(id).map(personneMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Personne : {}", id);
        personneRepository.deleteById(id);
        personneSearchRepository.deleteById(id);
    }

    @Override
    public Page<PersonneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Personnes for query {}", query);
        return personneSearchRepository.search(queryStringQuery(query), pageable).map(personneMapper::toDto);
    }
}

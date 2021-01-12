package rim.mr.covtest.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rim.mr.covtest.domain.TestCovid;
import rim.mr.covtest.repository.TestCovidRepository;
import rim.mr.covtest.repository.search.TestCovidSearchRepository;
import rim.mr.covtest.service.TestCovidService;
import rim.mr.covtest.service.dto.TestCovidDTO;
import rim.mr.covtest.service.mapper.TestCovidMapper;

/**
 * Service Implementation for managing {@link TestCovid}.
 */
@Service
public class TestCovidServiceImpl implements TestCovidService {
    private final Logger log = LoggerFactory.getLogger(TestCovidServiceImpl.class);

    private final TestCovidRepository testCovidRepository;

    private final TestCovidMapper testCovidMapper;

    private final TestCovidSearchRepository testCovidSearchRepository;

    public TestCovidServiceImpl(
        TestCovidRepository testCovidRepository,
        TestCovidMapper testCovidMapper,
        TestCovidSearchRepository testCovidSearchRepository
    ) {
        this.testCovidRepository = testCovidRepository;
        this.testCovidMapper = testCovidMapper;
        this.testCovidSearchRepository = testCovidSearchRepository;
    }

    @Override
    public TestCovidDTO save(TestCovidDTO testCovidDTO) {
        log.debug("Request to save TestCovid : {}", testCovidDTO);
        TestCovid testCovid = testCovidMapper.toEntity(testCovidDTO);
        testCovid = testCovidRepository.save(testCovid);
        TestCovidDTO result = testCovidMapper.toDto(testCovid);
        testCovidSearchRepository.save(testCovid);
        return result;
    }

    @Override
    public Page<TestCovidDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TestCovids");
        return testCovidRepository.findAll(pageable).map(testCovidMapper::toDto);
    }

    @Override
    public Optional<TestCovidDTO> findOne(String id) {
        log.debug("Request to get TestCovid : {}", id);
        return testCovidRepository.findById(id).map(testCovidMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete TestCovid : {}", id);
        testCovidRepository.deleteById(id);
        testCovidSearchRepository.deleteById(id);
    }

    @Override
    public Page<TestCovidDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TestCovids for query {}", query);
        return testCovidSearchRepository.search(queryStringQuery(query), pageable).map(testCovidMapper::toDto);
    }
}

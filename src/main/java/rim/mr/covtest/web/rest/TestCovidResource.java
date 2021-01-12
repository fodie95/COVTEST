package rim.mr.covtest.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.*;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import rim.mr.covtest.service.TestCovidService;
import rim.mr.covtest.service.dto.TestCovidDTO;
import rim.mr.covtest.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link rim.mr.covtest.domain.TestCovid}.
 */
@RestController
@RequestMapping("/api")
public class TestCovidResource {
    private final Logger log = LoggerFactory.getLogger(TestCovidResource.class);

    private static final String ENTITY_NAME = "testCovid";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TestCovidService testCovidService;

    public TestCovidResource(TestCovidService testCovidService) {
        this.testCovidService = testCovidService;
    }

    /**
     * {@code POST  /test-covids} : Create a new testCovid.
     *
     * @param testCovidDTO the testCovidDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new testCovidDTO, or with status {@code 400 (Bad Request)} if the testCovid has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/test-covids")
    public ResponseEntity<TestCovidDTO> createTestCovid(@Valid @RequestBody TestCovidDTO testCovidDTO) throws URISyntaxException {
        log.debug("REST request to save TestCovid : {}", testCovidDTO);
        if (testCovidDTO.getId() != null) {
            throw new BadRequestAlertException("A new testCovid cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TestCovidDTO result = testCovidService.save(testCovidDTO);
        return ResponseEntity
            .created(new URI("/api/test-covids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /test-covids} : Updates an existing testCovid.
     *
     * @param testCovidDTO the testCovidDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated testCovidDTO,
     * or with status {@code 400 (Bad Request)} if the testCovidDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the testCovidDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/test-covids")
    public ResponseEntity<TestCovidDTO> updateTestCovid(@Valid @RequestBody TestCovidDTO testCovidDTO) throws URISyntaxException {
        log.debug("REST request to update TestCovid : {}", testCovidDTO);
        if (testCovidDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TestCovidDTO result = testCovidService.save(testCovidDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, testCovidDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /test-covids} : get all the testCovids.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of testCovids in body.
     */
    @GetMapping("/test-covids")
    public ResponseEntity<List<TestCovidDTO>> getAllTestCovids(Pageable pageable) {
        log.debug("REST request to get a page of TestCovids");
        Page<TestCovidDTO> page = testCovidService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /test-covids/:id} : get the "id" testCovid.
     *
     * @param id the id of the testCovidDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the testCovidDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/test-covids/{id}")
    public ResponseEntity<TestCovidDTO> getTestCovid(@PathVariable String id) {
        log.debug("REST request to get TestCovid : {}", id);
        Optional<TestCovidDTO> testCovidDTO = testCovidService.findOne(id);
        return ResponseUtil.wrapOrNotFound(testCovidDTO);
    }

    /**
     * {@code DELETE  /test-covids/:id} : delete the "id" testCovid.
     *
     * @param id the id of the testCovidDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/test-covids/{id}")
    public ResponseEntity<Void> deleteTestCovid(@PathVariable String id) {
        log.debug("REST request to delete TestCovid : {}", id);
        testCovidService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/test-covids?query=:query} : search for the testCovid corresponding
     * to the query.
     *
     * @param query the query of the testCovid search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/test-covids")
    public ResponseEntity<List<TestCovidDTO>> searchTestCovids(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TestCovids for query {}", query);
        Page<TestCovidDTO> page = testCovidService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

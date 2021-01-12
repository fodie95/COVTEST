package rim.mr.covtest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import rim.mr.covtest.CovtestApp;
import rim.mr.covtest.domain.TestCovid;
import rim.mr.covtest.repository.TestCovidRepository;
import rim.mr.covtest.repository.search.TestCovidSearchRepository;
import rim.mr.covtest.service.TestCovidService;
import rim.mr.covtest.service.dto.TestCovidDTO;
import rim.mr.covtest.service.mapper.TestCovidMapper;

/**
 * Integration tests for the {@link TestCovidResource} REST controller.
 */
@SpringBootTest(classes = CovtestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TestCovidResourceIT {
    private static final Boolean DEFAULT_POSITIVE = false;
    private static final Boolean UPDATED_POSITIVE = true;

    private static final Instant DEFAULT_DATE_TEST = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TEST = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_RESULAT_TEST = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESULAT_TEST = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESULAT_TEST_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESULAT_TEST_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SECRET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SECRET_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECUPERED = false;
    private static final Boolean UPDATED_RECUPERED = true;

    @Autowired
    private TestCovidRepository testCovidRepository;

    @Autowired
    private TestCovidMapper testCovidMapper;

    @Autowired
    private TestCovidService testCovidService;

    /**
     * This repository is mocked in the rim.mr.covtest.repository.search test package.
     *
     * @see rim.mr.covtest.repository.search.TestCovidSearchRepositoryMockConfiguration
     */
    @Autowired
    private TestCovidSearchRepository mockTestCovidSearchRepository;

    @Autowired
    private MockMvc restTestCovidMockMvc;

    private TestCovid testCovid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestCovid createEntity() {
        TestCovid testCovid = new TestCovid()
            .positive(DEFAULT_POSITIVE)
            .dateTest(DEFAULT_DATE_TEST)
            .resulatTest(DEFAULT_RESULAT_TEST)
            .resulatTestContentType(DEFAULT_RESULAT_TEST_CONTENT_TYPE)
            .secretKey(DEFAULT_SECRET_KEY)
            .recupered(DEFAULT_RECUPERED);
        return testCovid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TestCovid createUpdatedEntity() {
        TestCovid testCovid = new TestCovid()
            .positive(UPDATED_POSITIVE)
            .dateTest(UPDATED_DATE_TEST)
            .resulatTest(UPDATED_RESULAT_TEST)
            .resulatTestContentType(UPDATED_RESULAT_TEST_CONTENT_TYPE)
            .secretKey(UPDATED_SECRET_KEY)
            .recupered(UPDATED_RECUPERED);
        return testCovid;
    }

    @BeforeEach
    public void initTest() {
        testCovidRepository.deleteAll();
        testCovid = createEntity();
    }

    @Test
    public void createTestCovid() throws Exception {
        int databaseSizeBeforeCreate = testCovidRepository.findAll().size();
        // Create the TestCovid
        TestCovidDTO testCovidDTO = testCovidMapper.toDto(testCovid);
        restTestCovidMockMvc
            .perform(
                post("/api/test-covids").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCovidDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TestCovid in the database
        List<TestCovid> testCovidList = testCovidRepository.findAll();
        assertThat(testCovidList).hasSize(databaseSizeBeforeCreate + 1);
        TestCovid testTestCovid = testCovidList.get(testCovidList.size() - 1);
        assertThat(testTestCovid.isPositive()).isEqualTo(DEFAULT_POSITIVE);
        assertThat(testTestCovid.getDateTest()).isEqualTo(DEFAULT_DATE_TEST);
        assertThat(testTestCovid.getResulatTest()).isEqualTo(DEFAULT_RESULAT_TEST);
        assertThat(testTestCovid.getResulatTestContentType()).isEqualTo(DEFAULT_RESULAT_TEST_CONTENT_TYPE);
        assertThat(testTestCovid.getSecretKey()).isEqualTo(DEFAULT_SECRET_KEY);
        assertThat(testTestCovid.isRecupered()).isEqualTo(DEFAULT_RECUPERED);

        // Validate the TestCovid in Elasticsearch
        verify(mockTestCovidSearchRepository, times(1)).save(testTestCovid);
    }

    @Test
    public void createTestCovidWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = testCovidRepository.findAll().size();

        // Create the TestCovid with an existing ID
        testCovid.setId("existing_id");
        TestCovidDTO testCovidDTO = testCovidMapper.toDto(testCovid);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTestCovidMockMvc
            .perform(
                post("/api/test-covids").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCovidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCovid in the database
        List<TestCovid> testCovidList = testCovidRepository.findAll();
        assertThat(testCovidList).hasSize(databaseSizeBeforeCreate);

        // Validate the TestCovid in Elasticsearch
        verify(mockTestCovidSearchRepository, times(0)).save(testCovid);
    }

    @Test
    public void getAllTestCovids() throws Exception {
        // Initialize the database
        testCovidRepository.save(testCovid);

        // Get all the testCovidList
        restTestCovidMockMvc
            .perform(get("/api/test-covids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testCovid.getId())))
            .andExpect(jsonPath("$.[*].positive").value(hasItem(DEFAULT_POSITIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateTest").value(hasItem(DEFAULT_DATE_TEST.toString())))
            .andExpect(jsonPath("$.[*].resulatTestContentType").value(hasItem(DEFAULT_RESULAT_TEST_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resulatTest").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESULAT_TEST))))
            .andExpect(jsonPath("$.[*].secretKey").value(hasItem(DEFAULT_SECRET_KEY)))
            .andExpect(jsonPath("$.[*].recupered").value(hasItem(DEFAULT_RECUPERED.booleanValue())));
    }

    @Test
    public void getTestCovid() throws Exception {
        // Initialize the database
        testCovidRepository.save(testCovid);

        // Get the testCovid
        restTestCovidMockMvc
            .perform(get("/api/test-covids/{id}", testCovid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(testCovid.getId()))
            .andExpect(jsonPath("$.positive").value(DEFAULT_POSITIVE.booleanValue()))
            .andExpect(jsonPath("$.dateTest").value(DEFAULT_DATE_TEST.toString()))
            .andExpect(jsonPath("$.resulatTestContentType").value(DEFAULT_RESULAT_TEST_CONTENT_TYPE))
            .andExpect(jsonPath("$.resulatTest").value(Base64Utils.encodeToString(DEFAULT_RESULAT_TEST)))
            .andExpect(jsonPath("$.secretKey").value(DEFAULT_SECRET_KEY))
            .andExpect(jsonPath("$.recupered").value(DEFAULT_RECUPERED.booleanValue()));
    }

    @Test
    public void getNonExistingTestCovid() throws Exception {
        // Get the testCovid
        restTestCovidMockMvc.perform(get("/api/test-covids/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void updateTestCovid() throws Exception {
        // Initialize the database
        testCovidRepository.save(testCovid);

        int databaseSizeBeforeUpdate = testCovidRepository.findAll().size();

        // Update the testCovid
        TestCovid updatedTestCovid = testCovidRepository.findById(testCovid.getId()).get();
        updatedTestCovid
            .positive(UPDATED_POSITIVE)
            .dateTest(UPDATED_DATE_TEST)
            .resulatTest(UPDATED_RESULAT_TEST)
            .resulatTestContentType(UPDATED_RESULAT_TEST_CONTENT_TYPE)
            .secretKey(UPDATED_SECRET_KEY)
            .recupered(UPDATED_RECUPERED);
        TestCovidDTO testCovidDTO = testCovidMapper.toDto(updatedTestCovid);

        restTestCovidMockMvc
            .perform(
                put("/api/test-covids").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCovidDTO))
            )
            .andExpect(status().isOk());

        // Validate the TestCovid in the database
        List<TestCovid> testCovidList = testCovidRepository.findAll();
        assertThat(testCovidList).hasSize(databaseSizeBeforeUpdate);
        TestCovid testTestCovid = testCovidList.get(testCovidList.size() - 1);
        assertThat(testTestCovid.isPositive()).isEqualTo(UPDATED_POSITIVE);
        assertThat(testTestCovid.getDateTest()).isEqualTo(UPDATED_DATE_TEST);
        assertThat(testTestCovid.getResulatTest()).isEqualTo(UPDATED_RESULAT_TEST);
        assertThat(testTestCovid.getResulatTestContentType()).isEqualTo(UPDATED_RESULAT_TEST_CONTENT_TYPE);
        assertThat(testTestCovid.getSecretKey()).isEqualTo(UPDATED_SECRET_KEY);
        assertThat(testTestCovid.isRecupered()).isEqualTo(UPDATED_RECUPERED);

        // Validate the TestCovid in Elasticsearch
        verify(mockTestCovidSearchRepository, times(1)).save(testTestCovid);
    }

    @Test
    public void updateNonExistingTestCovid() throws Exception {
        int databaseSizeBeforeUpdate = testCovidRepository.findAll().size();

        // Create the TestCovid
        TestCovidDTO testCovidDTO = testCovidMapper.toDto(testCovid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTestCovidMockMvc
            .perform(
                put("/api/test-covids").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(testCovidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TestCovid in the database
        List<TestCovid> testCovidList = testCovidRepository.findAll();
        assertThat(testCovidList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TestCovid in Elasticsearch
        verify(mockTestCovidSearchRepository, times(0)).save(testCovid);
    }

    @Test
    public void deleteTestCovid() throws Exception {
        // Initialize the database
        testCovidRepository.save(testCovid);

        int databaseSizeBeforeDelete = testCovidRepository.findAll().size();

        // Delete the testCovid
        restTestCovidMockMvc
            .perform(delete("/api/test-covids/{id}", testCovid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TestCovid> testCovidList = testCovidRepository.findAll();
        assertThat(testCovidList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TestCovid in Elasticsearch
        verify(mockTestCovidSearchRepository, times(1)).deleteById(testCovid.getId());
    }

    @Test
    public void searchTestCovid() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        testCovidRepository.save(testCovid);
        when(mockTestCovidSearchRepository.search(queryStringQuery("id:" + testCovid.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(testCovid), PageRequest.of(0, 1), 1));

        // Search the testCovid
        restTestCovidMockMvc
            .perform(get("/api/_search/test-covids?query=id:" + testCovid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(testCovid.getId())))
            .andExpect(jsonPath("$.[*].positive").value(hasItem(DEFAULT_POSITIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].dateTest").value(hasItem(DEFAULT_DATE_TEST.toString())))
            .andExpect(jsonPath("$.[*].resulatTestContentType").value(hasItem(DEFAULT_RESULAT_TEST_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resulatTest").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESULAT_TEST))))
            .andExpect(jsonPath("$.[*].secretKey").value(hasItem(DEFAULT_SECRET_KEY)))
            .andExpect(jsonPath("$.[*].recupered").value(hasItem(DEFAULT_RECUPERED.booleanValue())));
    }
}

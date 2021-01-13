package rim.mr.covtest.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import rim.mr.covtest.CovtestApp;
import rim.mr.covtest.domain.Personne;
import rim.mr.covtest.repository.PersonneRepository;
import rim.mr.covtest.repository.search.PersonneSearchRepository;
import rim.mr.covtest.service.PersonneService;
import rim.mr.covtest.service.dto.PersonneDTO;
import rim.mr.covtest.service.mapper.PersonneMapper;

/**
 * Integration tests for the {@link PersonneResource} REST controller.
 */
@SpringBootTest(classes = CovtestApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonneResourceIT {
    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NNI = "AAAAAAAAAA";
    private static final String UPDATED_NNI = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_WHATSAPP = "AAAAAAAAAA";
    private static final String UPDATED_WHATSAPP = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private PersonneRepository personneRepository;

    @Autowired
    private PersonneMapper personneMapper;

    @Autowired
    private PersonneService personneService;

    /**
     * This repository is mocked in the rim.mr.covtest.repository.search test package.
     *
     * @see rim.mr.covtest.repository.search.PersonneSearchRepositoryMockConfiguration
     */
    @Autowired
    private PersonneSearchRepository mockPersonneSearchRepository;

    @Autowired
    private MockMvc restPersonneMockMvc;

    private Personne personne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createEntity() {
        Personne personne = new Personne()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .nni(DEFAULT_NNI)
            .tel(DEFAULT_TEL)
            .whatsapp(DEFAULT_WHATSAPP)
            .email(DEFAULT_EMAIL)
            .adresse(DEFAULT_ADRESSE);
        return personne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personne createUpdatedEntity() {
        Personne personne = new Personne()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nni(UPDATED_NNI)
            .tel(UPDATED_TEL)
            .whatsapp(UPDATED_WHATSAPP)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE);
        return personne;
    }

    @BeforeEach
    public void initTest() {
        personneRepository.deleteAll();
        personne = createEntity();
    }

    @Test
    public void createPersonne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();
        // Create the Personne
        PersonneDTO personneDTO = personneMapper.toDto(personne);
        restPersonneMockMvc
            .perform(post("/api/personnes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isCreated());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPersonne.getNni()).isEqualTo(DEFAULT_NNI);
        assertThat(testPersonne.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testPersonne.getWhatsapp()).isEqualTo(DEFAULT_WHATSAPP);
        assertThat(testPersonne.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonne.getAdresse()).isEqualTo(DEFAULT_ADRESSE);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(1)).save(testPersonne);
    }

    @Test
    public void createPersonneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne with an existing ID
        personne.setId("existing_id");
        PersonneDTO personneDTO = personneMapper.toDto(personne);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonneMockMvc
            .perform(post("/api/personnes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeCreate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    public void getAllPersonnes() throws Exception {
        // Initialize the database
        personneRepository.save(personne);

        // Get all the personneList
        restPersonneMockMvc
            .perform(get("/api/personnes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nni").value(hasItem(DEFAULT_NNI)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));
    }

    @Test
    public void getPersonne() throws Exception {
        // Initialize the database
        personneRepository.save(personne);

        // Get the personne
        restPersonneMockMvc
            .perform(get("/api/personnes/{id}", personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personne.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nni").value(DEFAULT_NNI))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.whatsapp").value(DEFAULT_WHATSAPP))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE));
    }

    @Test
    public void getNonExistingPersonne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/api/personnes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void updatePersonne() throws Exception {
        // Initialize the database
        personneRepository.save(personne);

        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        Personne updatedPersonne = personneRepository.findById(personne.getId()).get();
        updatedPersonne
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nni(UPDATED_NNI)
            .tel(UPDATED_TEL)
            .whatsapp(UPDATED_WHATSAPP)
            .email(UPDATED_EMAIL)
            .adresse(UPDATED_ADRESSE);
        PersonneDTO personneDTO = personneMapper.toDto(updatedPersonne);

        restPersonneMockMvc
            .perform(put("/api/personnes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isOk());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personneList.get(personneList.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPersonne.getNni()).isEqualTo(UPDATED_NNI);
        assertThat(testPersonne.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testPersonne.getWhatsapp()).isEqualTo(UPDATED_WHATSAPP);
        assertThat(testPersonne.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonne.getAdresse()).isEqualTo(UPDATED_ADRESSE);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(1)).save(testPersonne);
    }

    @Test
    public void updateNonExistingPersonne() throws Exception {
        int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Create the Personne
        PersonneDTO personneDTO = personneMapper.toDto(personne);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonneMockMvc
            .perform(put("/api/personnes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Personne in the database
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(0)).save(personne);
    }

    @Test
    public void deletePersonne() throws Exception {
        // Initialize the database
        personneRepository.save(personne);

        int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Delete the personne
        restPersonneMockMvc
            .perform(delete("/api/personnes/{id}", personne.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Personne> personneList = personneRepository.findAll();
        assertThat(personneList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Personne in Elasticsearch
        verify(mockPersonneSearchRepository, times(1)).deleteById(personne.getId());
    }

    @Test
    public void searchPersonne() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        personneRepository.save(personne);
        when(mockPersonneSearchRepository.search(queryStringQuery("id:" + personne.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(personne), PageRequest.of(0, 1), 1));

        // Search the personne
        restPersonneMockMvc
            .perform(get("/api/_search/personnes?query=id:" + personne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nni").value(hasItem(DEFAULT_NNI)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].whatsapp").value(hasItem(DEFAULT_WHATSAPP)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)));
    }
}

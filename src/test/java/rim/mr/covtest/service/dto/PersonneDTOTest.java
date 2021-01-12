package rim.mr.covtest.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rim.mr.covtest.web.rest.TestUtil;

public class PersonneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonneDTO.class);
        PersonneDTO personneDTO1 = new PersonneDTO();
        personneDTO1.setId("id1");
        PersonneDTO personneDTO2 = new PersonneDTO();
        assertThat(personneDTO1).isNotEqualTo(personneDTO2);
        personneDTO2.setId(personneDTO1.getId());
        assertThat(personneDTO1).isEqualTo(personneDTO2);
        personneDTO2.setId("id2");
        assertThat(personneDTO1).isNotEqualTo(personneDTO2);
        personneDTO1.setId(null);
        assertThat(personneDTO1).isNotEqualTo(personneDTO2);
    }
}

package rim.mr.covtest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rim.mr.covtest.web.rest.TestUtil;

public class PersonneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Personne.class);
        Personne personne1 = new Personne();
        personne1.setId("id1");
        Personne personne2 = new Personne();
        personne2.setId(personne1.getId());
        assertThat(personne1).isEqualTo(personne2);
        personne2.setId("id2");
        assertThat(personne1).isNotEqualTo(personne2);
        personne1.setId(null);
        assertThat(personne1).isNotEqualTo(personne2);
    }
}

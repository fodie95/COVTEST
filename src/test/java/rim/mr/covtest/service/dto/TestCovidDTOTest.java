package rim.mr.covtest.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rim.mr.covtest.web.rest.TestUtil;

public class TestCovidDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestCovidDTO.class);
        TestCovidDTO testCovidDTO1 = new TestCovidDTO();
        testCovidDTO1.setId("id1");
        TestCovidDTO testCovidDTO2 = new TestCovidDTO();
        assertThat(testCovidDTO1).isNotEqualTo(testCovidDTO2);
        testCovidDTO2.setId(testCovidDTO1.getId());
        assertThat(testCovidDTO1).isEqualTo(testCovidDTO2);
        testCovidDTO2.setId("id2");
        assertThat(testCovidDTO1).isNotEqualTo(testCovidDTO2);
        testCovidDTO1.setId(null);
        assertThat(testCovidDTO1).isNotEqualTo(testCovidDTO2);
    }
}

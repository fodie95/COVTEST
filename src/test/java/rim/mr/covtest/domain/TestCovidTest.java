package rim.mr.covtest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rim.mr.covtest.web.rest.TestUtil;

public class TestCovidTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TestCovid.class);
        TestCovid testCovid1 = new TestCovid();
        testCovid1.setId("id1");
        TestCovid testCovid2 = new TestCovid();
        testCovid2.setId(testCovid1.getId());
        assertThat(testCovid1).isEqualTo(testCovid2);
        testCovid2.setId("id2");
        assertThat(testCovid1).isNotEqualTo(testCovid2);
        testCovid1.setId(null);
        assertThat(testCovid1).isNotEqualTo(testCovid2);
    }
}

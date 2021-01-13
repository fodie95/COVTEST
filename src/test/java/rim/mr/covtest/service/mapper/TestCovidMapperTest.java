package rim.mr.covtest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCovidMapperTest {
    private TestCovidMapper testCovidMapper;

    @BeforeEach
    public void setUp() {
        testCovidMapper = new TestCovidMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(testCovidMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(testCovidMapper.fromId(null)).isNull();
    }
}

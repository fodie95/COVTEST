package rim.mr.covtest.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonneMapperTest {
    private PersonneMapper personneMapper;

    @BeforeEach
    public void setUp() {
        personneMapper = new PersonneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(personneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(personneMapper.fromId(null)).isNull();
    }
}

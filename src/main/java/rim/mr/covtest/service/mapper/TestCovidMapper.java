package rim.mr.covtest.service.mapper;

import org.mapstruct.*;
import rim.mr.covtest.domain.*;
import rim.mr.covtest.service.dto.TestCovidDTO;

/**
 * Mapper for the entity {@link TestCovid} and its DTO {@link TestCovidDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonneMapper.class })
public interface TestCovidMapper extends EntityMapper<TestCovidDTO, TestCovid> {
    @Mapping(source = "personne.id", target = "personneId")
    TestCovidDTO toDto(TestCovid testCovid);

    @Mapping(source = "personneId", target = "personne")
    TestCovid toEntity(TestCovidDTO testCovidDTO);

    default TestCovid fromId(String id) {
        if (id == null) {
            return null;
        }
        TestCovid testCovid = new TestCovid();
        testCovid.setId(id);
        return testCovid;
    }
}

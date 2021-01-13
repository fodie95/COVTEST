package rim.mr.covtest.service.mapper;

import org.mapstruct.*;
import rim.mr.covtest.domain.*;
import rim.mr.covtest.service.dto.PersonneDTO;

/**
 * Mapper for the entity {@link Personne} and its DTO {@link PersonneDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonneMapper extends EntityMapper<PersonneDTO, Personne> {
    default Personne fromId(String id) {
        if (id == null) {
            return null;
        }
        Personne personne = new Personne();
        personne.setId(id);
        return personne;
    }
}

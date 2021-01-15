package rim.mr.covtest.service.dto;

import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rim.mr.covtest.domain.TestCovid} entity.
 */
public class TestCovidDTO implements Serializable {
    private String id;

    private Boolean positive;

    private Instant dateTest;
    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    private byte[] resulatTest;

    private String resulatTestContentType;
    private String secretKey;

    private Boolean recupered;

    private String personneId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isPositive() {
        return positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public Instant getDateTest() {
        return dateTest;
    }

    public void setDateTest(Instant dateTest) {
        this.dateTest = dateTest;
    }

    public byte[] getResulatTest() {
        return resulatTest;
    }

    public void setResulatTest(byte[] resulatTest) {
        this.resulatTest = resulatTest;
    }

    public String getResulatTestContentType() {
        return resulatTestContentType;
    }

    public void setResulatTestContentType(String resulatTestContentType) {
        this.resulatTestContentType = resulatTestContentType;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Boolean isRecupered() {
        return recupered;
    }

    public void setRecupered(Boolean recupered) {
        this.recupered = recupered;
    }

    public String getPersonneId() {
        return personneId;
    }

    public void setPersonneId(String personneId) {
        this.personneId = personneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCovidDTO)) {
            return false;
        }

        return id != null && id.equals(((TestCovidDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCovidDTO{" +
            "id=" + getId() +
            ", positive='" + isPositive() + "'" +
            ", dateTest='" + getDateTest() + "'" +
            ", resulatTest='" + getResulatTest() + "'" +
            ", secretKey='" + getSecretKey() + "'" +
            ", recupered='" + isRecupered() + "'" +
            ", personneId='" + getPersonneId() + "'" +
            "}";
    }
}

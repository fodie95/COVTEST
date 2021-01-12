package rim.mr.covtest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A TestCovid.
 */
@Document(collection = "test_covid")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "testcovid")
public class TestCovid implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("positive")
    private Boolean positive;

    @Field("date_test")
    private Instant dateTest;

    @Field("resulat_test")
    private byte[] resulatTest;

    @Field("resulat_test_content_type")
    private String resulatTestContentType;

    @Field("secret_key")
    private String secretKey;

    @Field("recupered")
    private Boolean recupered;

    @DBRef
    @Field("personne")
    @JsonIgnoreProperties(value = "testCovids", allowSetters = true)
    private Personne personne;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isPositive() {
        return positive;
    }

    public TestCovid positive(Boolean positive) {
        this.positive = positive;
        return this;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public Instant getDateTest() {
        return dateTest;
    }

    public TestCovid dateTest(Instant dateTest) {
        this.dateTest = dateTest;
        return this;
    }

    public void setDateTest(Instant dateTest) {
        this.dateTest = dateTest;
    }

    public byte[] getResulatTest() {
        return resulatTest;
    }

    public TestCovid resulatTest(byte[] resulatTest) {
        this.resulatTest = resulatTest;
        return this;
    }

    public void setResulatTest(byte[] resulatTest) {
        this.resulatTest = resulatTest;
    }

    public String getResulatTestContentType() {
        return resulatTestContentType;
    }

    public TestCovid resulatTestContentType(String resulatTestContentType) {
        this.resulatTestContentType = resulatTestContentType;
        return this;
    }

    public void setResulatTestContentType(String resulatTestContentType) {
        this.resulatTestContentType = resulatTestContentType;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public TestCovid secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Boolean isRecupered() {
        return recupered;
    }

    public TestCovid recupered(Boolean recupered) {
        this.recupered = recupered;
        return this;
    }

    public void setRecupered(Boolean recupered) {
        this.recupered = recupered;
    }

    public Personne getPersonne() {
        return personne;
    }

    public TestCovid personne(Personne personne) {
        this.personne = personne;
        return this;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TestCovid)) {
            return false;
        }
        return id != null && id.equals(((TestCovid) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TestCovid{" +
            "id=" + getId() +
            ", positive='" + isPositive() + "'" +
            ", dateTest='" + getDateTest() + "'" +
            ", resulatTest='" + getResulatTest() + "'" +
            ", resulatTestContentType='" + getResulatTestContentType() + "'" +
            ", secretKey='" + getSecretKey() + "'" +
            ", recupered='" + isRecupered() + "'" +
            "}";
    }
}

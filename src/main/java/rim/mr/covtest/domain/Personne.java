package rim.mr.covtest.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Personne.
 */
@Document(collection = "personne")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "personne")
public class Personne implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("nni")
    private String nni;

    @Field("tel")
    private String tel;

    @Field("whatsapp")
    private String whatsapp;

    @Field("email")
    private String email;

    @Field("adresse")
    private String adresse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Personne nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Personne prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNni() {
        return nni;
    }

    public Personne nni(String nni) {
        this.nni = nni;
        return this;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public String getTel() {
        return tel;
    }

    public Personne tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public Personne whatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
        return this;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public Personne email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public Personne adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Personne)) {
            return false;
        }
        return id != null && id.equals(((Personne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Personne{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nni='" + getNni() + "'" +
            ", tel='" + getTel() + "'" +
            ", whatsapp='" + getWhatsapp() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}

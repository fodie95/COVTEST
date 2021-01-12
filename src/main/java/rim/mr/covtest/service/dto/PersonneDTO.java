package rim.mr.covtest.service.dto;

import java.io.Serializable;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link rim.mr.covtest.domain.Personne} entity.
 */
public class PersonneDTO implements Serializable {
    private String id;

    private String nom;

    private String prenom;

    private String nni;

    private String tel;

    private String whatsapp;

    private String email;

    private String adresse;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNni() {
        return nni;
    }

    public void setNni(String nni) {
        this.nni = nni;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonneDTO)) {
            return false;
        }

        return id != null && id.equals(((PersonneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonneDTO{" +
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

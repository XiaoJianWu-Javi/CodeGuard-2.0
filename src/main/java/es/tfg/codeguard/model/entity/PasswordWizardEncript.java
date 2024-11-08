package es.tfg.codeguard.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PasswordWizardEncript {
    @Id
    private String wizardName;
    private boolean elderCheck;
    private String hashedPass;

    public PasswordWizardEncript() {
    }

    public String getWizardName() {
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        this.wizardName = wizardName;
    }

    public boolean isElderCheck() {
        return elderCheck;
    }

    public void setElderCheck(boolean elderCheck) {
        this.elderCheck = elderCheck;
    }

    public String getHashedPass() {
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        this.hashedPass = hashedPass;
    }
}

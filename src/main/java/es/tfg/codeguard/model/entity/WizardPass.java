package es.tfg.codeguard.model.entity;

import java.util.NoSuchElementException;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class WizardPass {

    private static final String WIZARDNAME_REGEXP = "^[a-zA-Z]{3,}\\w*$";

    @Id
    @NotBlank
    @Pattern(regexp = WIZARDNAME_REGEXP, message = "{wizardname.invalidPattern}")
    private String wizardName;

    @NotBlank
    private String hashedPass;

    private Boolean elder;

    public WizardPass() {
        setElder(false);
    }

    public String getWizardName() {
        if (wizardName == null) throw new NoSuchElementException();
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        if (wizardName == null || wizardName.isBlank()) throw new IllegalArgumentException();
        if (!wizardName.matches(WIZARDNAME_REGEXP)) throw new IllegalArgumentException();
        this.wizardName = wizardName;
    }

    public String getHashedPass() {
        if (hashedPass == null) throw new NoSuchElementException();
        return hashedPass;
    }

    public void setHashedPass(String hashedPass) {
        if (hashedPass == null || hashedPass.isBlank()) throw new IllegalArgumentException();
        this.hashedPass = hashedPass;
    }

    public Boolean isElder() {
        return elder;
    }

    public void setElder(Boolean elder) {
        this.elder = elder;
    }

}
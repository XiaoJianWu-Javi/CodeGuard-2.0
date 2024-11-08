package es.tfg.codeguard.model.dto;

public class PasswordWizardDTO {
    private String wizardName;
    private boolean elderCheck;

    public PasswordWizardDTO() {
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
}

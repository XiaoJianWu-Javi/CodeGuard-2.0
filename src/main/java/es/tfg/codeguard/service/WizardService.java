package es.tfg.codeguard.service;

import es.tfg.codeguard.model.WizardDTO;

import java.util.Optional;

public interface WizardService {

    public Optional<WizardDTO> getWizardById(String wizardName);
    public Optional<WizardDTO> loginWizard(String wizardName, String wizardPassword);
    public Optional<WizardDTO> registerWizard(String wizardName, String wizardPassword);
    public Optional<WizardDTO> deleteWizard(String wizardName);
    public Optional<WizardDTO> logoutWizard(String wizardName);
    public Optional<WizardDTO> updateWizard(String wizardName, String newWizardPassword);

}

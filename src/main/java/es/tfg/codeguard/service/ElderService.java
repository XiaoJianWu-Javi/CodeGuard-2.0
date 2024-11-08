package es.tfg.codeguard.service;

import es.tfg.codeguard.model.WizardDTO;

import java.util.List;
import java.util.Optional;

public interface ElderService {

    public Optional<WizardDTO> deleteWizardById(String wizardName);
    public List<WizardDTO> getAllWizards();




}

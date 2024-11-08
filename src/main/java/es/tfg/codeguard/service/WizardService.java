package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.entity.DeadWizard;
import es.tfg.codeguard.model.entity.PasswordWizardEncript;
import es.tfg.codeguard.model.entity.Wizard;
import es.tfg.codeguard.model.repository.DeadWizardRepository;
import es.tfg.codeguard.model.repository.PasswordWizardRepository;
import es.tfg.codeguard.model.repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class WizardService {

    //configurar las clases para cuando se tenga configurado la base de datos utilizar bcript desde config
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private WizardRepository wizardRepository;
    @Autowired
    private PasswordWizardRepository passwordWizardRepository;
    @Autowired
    private DeadWizardRepository deadWizardRepository;


    public WizardService() {

    }

    public Optional<PasswordWizardDTO> registerWizard(String wizardname, String wizardpassword) {

        if (wizardRepository.findById(wizardname).isPresent()) {
            return Optional.empty();
        }

        Wizard wizard = new Wizard();
        wizard.setWizardName(wizardname);
        wizard.setTester(false);
        wizard.setCreator(false);
        wizard.setSpells(Collections.emptyList());
        wizardRepository.save(wizard);

        PasswordWizardEncript passwordWizardEncript = new PasswordWizardEncript();
        passwordWizardEncript.setWizardName(wizardname);
        passwordWizardEncript.setElderCheck(false);
        passwordWizardEncript.setHashedPass(passwordEncoder.encode(wizardpassword));
        passwordWizardRepository.save(passwordWizardEncript);

        PasswordWizardDTO passwordWizard = new PasswordWizardDTO();
        passwordWizard.setWizardName(wizardname);
        passwordWizard.setElderCheck(false);
        return Optional.of(passwordWizard);
    }

    public Optional<DeadWizard> deleteWizard(String wizardname) {

        if(passwordWizardRepository.findById(wizardname).isEmpty() && wizardRepository.findById(wizardname).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardname).get();

        DeadWizard deadwizard = new DeadWizard();
        deadwizard.setWizardName(wizard.getWizardName());
        deadwizard.setTester(wizard.isTester());
        deadwizard.setCreator(wizard.isCreator());
        deadwizard.setSpells(wizard.getSpells());
        deadWizardRepository.save(deadwizard);

        wizardRepository.delete(wizardRepository.findById(wizardname).get());
        passwordWizardRepository.delete(passwordWizardRepository.findById(wizardname).get());

        return Optional.of(deadwizard);
    }

    //public Optional<PasswordWizardDTO> loginWizard(String wizardname, String wizardpassword) {
    //    return Optional.empty();
    //}

    //public Optional<PasswordWizardDTO> logoutWizard(String wizardname, String wizardpassword) {
    //    return Optional.empty();
    //}


}

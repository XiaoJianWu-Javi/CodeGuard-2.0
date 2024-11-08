package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.dto.WizardDTO;
import es.tfg.codeguard.model.entity.DeadWizard;
import es.tfg.codeguard.model.entity.Wizard;
import es.tfg.codeguard.model.entity.WizardPass;
import es.tfg.codeguard.model.repository.DeadWizardRepository;
import es.tfg.codeguard.model.repository.WizardPassRepository;
import es.tfg.codeguard.model.repository.WizardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElderService {

    //configurar las clases para cuando se tenga configurado la base de datos utilizar bcript desde config
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private WizardRepository wizardRepository;
    @Autowired
    private WizardPassRepository wizardPassRepository;
    @Autowired
    private DeadWizardRepository deadWizardRepository;



    public Optional<WizardDTO> killWizard(String wizardName) {


        if(wizardPassRepository.findById(wizardName).isEmpty() && wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();

        DeadWizard deadwizard = new DeadWizard();
        deadwizard.setWizardName(wizard.getWizardName());
        deadwizard.setTester(wizard.isTester());
        deadwizard.setCreator(wizard.isCreator());
//        deadwizard.setSpells(wizard.getSpells());

        deadWizardRepository.save(deadwizard);

        wizardRepository.delete(wizardRepository.findById(wizardName).get());
        wizardPassRepository.delete(wizardPassRepository.findById(wizardName).get());



        return Optional.of(new WizardDTO(deadwizard));
    }

    public Optional<PasswordWizardDTO> updateWizard(String wizardName, String newWizardPass){

        if(wizardPassRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        WizardPass newWizard = wizardPassRepository.findById(wizardName).get();

        newWizard.setHashedPass(passwordEncoder.encode(newWizardPass));

        wizardPassRepository.save(newWizard);

        PasswordWizardDTO passwordWizardDTO = new PasswordWizardDTO();
        passwordWizardDTO.setWizardName(newWizard.getWizardName());
        passwordWizardDTO.setElderCheck(newWizard.isElder());
        return Optional.of(passwordWizardDTO);

    }

    public Optional<Wizard> grantTester(String wizardName){

        if(wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();

        wizard.setTester(true);
        wizardRepository.save(wizard);

        return Optional.of(wizard);
    }

    public Optional<Wizard> grantCreator(String wizardName){

        if(wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();

        wizard.setCreator(true);
        wizardRepository.save(wizard);

        return Optional.of(wizard);
    }

    public Optional<Wizard> revokeTester(String wizardName){

        if(wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();

        wizard.setTester(false);
        wizardRepository.save(wizard);

        return Optional.of(wizard);
    }

    public Optional<Wizard> revokeCreator(String wizardName){

        if(wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();

        wizard.setCreator(false);
        wizardRepository.save(wizard);

        return Optional.of(wizard);
    }

    public Optional<Wizard> grantAllPrivileges(String wizardName){

        if(wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();
        //cuando este implementada la base de datos llamar a los 2 metodos que cambian los permisos y pedir el optional a la base de datos en el return
        wizard.setTester(true);
        wizard.setCreator(true);
        wizardRepository.save(wizard);

        return Optional.of(wizard);
    }

    public Optional<Wizard> revokeAllPrivileges(String wizardName){

        if(wizardRepository.findById(wizardName).isEmpty()){
            return Optional.empty();
        }

        Wizard wizard = wizardRepository.findById(wizardName).get();
        //cuando este implementada la base de datos llamar a los 2 metodos que cambian los permisos y pedir el optional a la base de datos en el return
        wizard.setTester(false);
        wizard.setCreator(false);
        wizardRepository.save(wizard);

        return Optional.of(wizard);
    }



}

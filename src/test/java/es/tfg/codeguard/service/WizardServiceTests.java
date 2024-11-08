package es.tfg.codeguard.service;


import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.entity.DeadWizard;
import es.tfg.codeguard.model.entity.Wizard;
import es.tfg.codeguard.model.entity.WizardPass;
import es.tfg.codeguard.model.repository.DeadWizardRepository;
import es.tfg.codeguard.model.repository.WizardPassRepository;
import es.tfg.codeguard.model.repository.WizardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@SpringBootTest
class WizardServiceTests {

    @Mock
    private WizardRepository wizardRepository;

    @Mock
    private WizardPassRepository wizardPassRepository;

    @Mock
    private DeadWizardRepository deadWizardRepository;
    @InjectMocks
    private WizardService wizardService;


    @Test
    public void TestFailWizardServiceRegisterMethod() {
        Wizard wizard = new Wizard();
        wizard.setWizardName("Gandalf");
        wizard.setTester(false);
        wizard.setCreator(false);
//        wizard.setSpells(Collections.emptyList());
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));

        Optional<PasswordWizardDTO> wizardopt = wizardService.registerWizard("Gandalf", "cantpass");

        assertThat(wizardopt).isEmpty();
    }

    @Test
    public void TestFineWizardServiceRegisterMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<PasswordWizardDTO> wizard = wizardService.registerWizard("Gandalf", "cantpass");

        PasswordWizardDTO passwordWizardDTO = new PasswordWizardDTO();
        passwordWizardDTO.setWizardName("Gandalf");
        passwordWizardDTO.setElderCheck(false);
        Optional<PasswordWizardDTO> wizardExpected = Optional.of(passwordWizardDTO);
        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);
    }

    @Test
    public void TestFailWizardServiceDeleteMethod() {
        when(wizardPassRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<DeadWizard> deadWizard = wizardService.deleteWizard("Gandalf");
        assertThat(deadWizard).isEmpty();
    }

    @Test
    public void TestFineWizardServiceDeleteMethod() {
        WizardPass passwordWizardEncript = new WizardPass();
        passwordWizardEncript.setWizardName("Gandalf");
        passwordWizardEncript.setElder(false);
        passwordWizardEncript.setHashedPass(new BCryptPasswordEncoder().encode("cantpass"));

        Optional<WizardPass> wizardpass = Optional.of(passwordWizardEncript);
        when(wizardPassRepository.findById("Gandalf")).thenReturn(wizardpass);

        Wizard wizard = new Wizard();
        wizard.setWizardName("Gandalf");
        wizard.setTester(false);
        wizard.setCreator(false);
//        wizard.setSpells(Collections.emptyList());
        Optional<Wizard> wizardopt = Optional.of(wizard);
        when(wizardRepository.findById("Gandalf")).thenReturn(wizardopt);

        Optional<DeadWizard> deadWizardopt = wizardService.deleteWizard("Gandalf");

        DeadWizard deadWizardExpected = new DeadWizard();
        deadWizardExpected.setWizardName("Gandalf");
        deadWizardExpected.setTester(false);
        deadWizardExpected.setCreator(false);
//        deadWizardExpected.setSpells(Collections.emptyList());

        assertThat(deadWizardopt.get()).usingRecursiveComparison().isEqualTo(deadWizardExpected);
    }


}

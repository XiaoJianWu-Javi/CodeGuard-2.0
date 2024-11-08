package es.tfg.codeguard.service;


import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.entity.DeadWizard;
import es.tfg.codeguard.model.entity.PasswordWizardEncript;
import es.tfg.codeguard.model.entity.Wizard;
import es.tfg.codeguard.model.repository.DeadWizardRepository;
import es.tfg.codeguard.model.repository.PasswordWizardRepository;
import es.tfg.codeguard.model.repository.WizardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@SpringBootTest
class WizardServiceTests {

    @Mock
    private WizardRepository wizardRepository;

    @Mock
    private PasswordWizardRepository passwordWizardRepository;

    @Mock
    private DeadWizardRepository deadWizardRepository;

    private WizardService wizardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wizardService = new WizardService();
    }

    @Test
    public void TestFailWizardServiceRegisterMethod() {
        Wizard wizard = new Wizard();
        wizard.setWizardName("Gandalf");
        wizard.setTester(false);
        wizard.setCreator(false);
        wizard.setSpells(Collections.emptyList());
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
        when(passwordWizardRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<DeadWizard> deadWizard = wizardService.deleteWizard("Gandalf");
        assertThat(deadWizard).isEmpty();
    }

    @Test
    public void TestFineWizardServiceDeleteMethod() {
        PasswordWizardEncript passwordWizardEncript = new PasswordWizardEncript();
        passwordWizardEncript.setWizardName("Gandalf");
        passwordWizardEncript.setElderCheck(false);
        passwordWizardEncript.setHashedPass(new BCryptPasswordEncoder().encode("cantpass"));

        Optional<PasswordWizardEncript> wizardpass = Optional.of(passwordWizardEncript);
        when(passwordWizardRepository.findById("Gandalf")).thenReturn(wizardpass);

        Wizard wizard = new Wizard();
        wizard.setWizardName("Gandalf");
        wizard.setTester(false);
        wizard.setCreator(false);
        wizard.setSpells(Collections.emptyList());
        Optional<Wizard> wizardopt = Optional.of(wizard);
        when(wizardRepository.findById("Gandalf")).thenReturn(wizardopt);

        Optional<DeadWizard> deadWizardopt = wizardService.deleteWizard("Gandalf");

        DeadWizard deadWizardExpected = new DeadWizard();
        deadWizardExpected.setWizardName("Gandalf");
        deadWizardExpected.setTester(false);
        deadWizardExpected.setCreator(false);
        deadWizardExpected.setSpells(Collections.emptyList());

        assertThat(deadWizardopt.get()).usingRecursiveComparison().isEqualTo(deadWizardExpected);
    }


}

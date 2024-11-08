package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.dto.WizardDTO;
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
class ElderServiceTests {

    @Mock

    private WizardRepository wizardRepository;

    @Mock

    private PasswordWizardRepository passwordWizardRepository;

    @Mock

    private DeadWizardRepository deadWizardRepository;

    private ElderService elderService;

    private PasswordWizardEncript passwordWizardEncript;

    private Wizard wizard;

    private DeadWizard deadWizard;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        elderService = new ElderService();

        passwordWizardEncript = new PasswordWizardEncript();
        passwordWizardEncript.setWizardName("Gandalf");
        passwordWizardEncript.setElderCheck(false);
        passwordWizardEncript.setHashedPass(new BCryptPasswordEncoder().encode("cantpass"));

        wizard = new Wizard();
        wizard.setWizardName("Gandalf");
        wizard.setTester(false);
        wizard.setCreator(false);
        wizard.setSpells(Collections.emptyList());

        deadWizard = new DeadWizard();
        deadWizard.setWizardName("Gandalf");
        deadWizard.setTester(false);
        deadWizard.setCreator(false);
        deadWizard.setSpells(Collections.emptyList());

    }


    @Test
    public void TestFailElderServiceKillMethod(){
        when(passwordWizardRepository.findById("Gandalf")).thenReturn(Optional.empty());

        Optional<WizardDTO> deadWizard = elderService.killWizard("Gandalf");
        assertThat(deadWizard).isEmpty();
    }

    @Test
    public void TestFineElderServiceKillMethod(){
        Optional<PasswordWizardEncript> wizardPass = Optional.of(passwordWizardEncript);
        when(passwordWizardRepository.findById("Gandalf")).thenReturn(wizardPass);


        Optional<Wizard> wizardOpt = Optional.of(wizard);
        when(wizardRepository.findById("Gandalf")).thenReturn(wizardOpt);

        Optional<WizardDTO> deadWizardOpt = elderService.killWizard("Gandalf");

        Optional<DeadWizard> deadWizardExpectedOpt = Optional.of(deadWizard);

        assertThat(deadWizardExpectedOpt).usingRecursiveComparison().isEqualTo(deadWizardOpt);



    }

    @Test
    public void TestFailElderServiceUpdateMethod() {
        when(passwordWizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<PasswordWizardDTO> wizard = elderService.updateWizard("Gandalf", "cantpass");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceUpdateMethod() {

        when(passwordWizardRepository.findById("Gandalf")).thenReturn(Optional.of(passwordWizardEncript));


        PasswordWizardDTO passwordWizardDTO = new PasswordWizardDTO();
        passwordWizardDTO.setWizardName("Gandalf");
        passwordWizardDTO.setElderCheck(false);
        Optional<PasswordWizardDTO> wizardExpected = Optional.of(passwordWizardDTO);
        Optional<PasswordWizardDTO> wizard = elderService.updateWizard("Gandalf", "cantpass");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }


    @Test
    public void TestFailElderServiceGrantTesterMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<Wizard> wizard = elderService.grantTester("Gandalf");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceGrantTesterMethod() {

        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));
        wizard.setTester(true);
        Optional<Wizard> wizardExpected = Optional.of(wizard);
        Optional<Wizard> wizard = elderService.grantTester("Gandalf");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }

    @Test
    public void TestFailElderServiceRevokeTesterMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<Wizard> wizard = elderService.revokeTester("Gandalf");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceRevokeTesterMethod() {
        wizard.setTester(true);
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));

        wizard.setTester(false);
        Optional<Wizard> wizardExpected = Optional.of( wizard);
        Optional<Wizard> wizard = elderService.revokeTester("Gandalf");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }

    @Test
    public void TestFailElderServiceGrantCreatorMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<Wizard> wizard = elderService.grantCreator("Gandalf");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceGrantCreatorMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));

        wizard.setCreator(true);
        Optional<Wizard> wizardExpected = Optional.of(wizard);
        Optional<Wizard> wizard = elderService.grantCreator("Gandalf");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }

    @Test
    public void TestFailElderServiceRevokeCreatorMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<Wizard> wizard = elderService.revokeCreator("Gandalf");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceRevokeCreatorMethod() {
        wizard.setCreator(true);
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));

        wizard.setCreator(false);
        Optional<Wizard> wizardExpected = Optional.of( wizard);
        Optional<Wizard> wizard = elderService.revokeCreator("Gandalf");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }


    @Test
    public void TestFailElderServiceGrantAllPrivilegesMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<Wizard> wizard = elderService.grantAllPrivileges("Gandalf");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceGrantAllPrivilegesMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));

        wizard.setCreator(true);
        wizard.setTester(true);
        Optional<Wizard> wizardExpected = Optional.of( wizard);
        Optional<Wizard> wizard = elderService.grantAllPrivileges("Gandalf");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }

    @Test
    public void TestFailElderServiceRevokeAllPrivilegesMethod() {
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.empty());
        Optional<Wizard> wizard = elderService.revokeAllPrivileges("Gandalf");

        assertThat(wizard).isEmpty();

    }

    @Test
    public void TestFineElderServiceRevokeAllPrivilegesMethod() {
        wizard.setCreator(true);
        wizard.setTester(true);
        when(wizardRepository.findById("Gandalf")).thenReturn(Optional.of(wizard));
        wizard.setCreator(false);
        wizard.setTester(false);
        Optional<Wizard> wizardExpected = Optional.of( wizard);
        Optional<Wizard> wizard = elderService.revokeAllPrivileges("Gandalf");

        assertThat(wizardExpected).usingRecursiveComparison().isEqualTo(wizard);

    }

}

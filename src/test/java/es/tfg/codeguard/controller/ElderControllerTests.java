package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.WizardDTO;
import es.tfg.codeguard.service.ElderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ElderControllerTests {

	@Mock
	private ElderService elderService;

	@InjectMocks
	//@AutoWired
	private ElderController elderController;

	@Mock
	WizardDTO wizardDTO;

	@BeforeEach
	void setup(){
		wizardDTO = new WizardDTO();
	}


	@ParameterizedTest
	@ValueSource(strings = {"FirstWizard","SecondWizard","ThirdWizard","FourthWizard"})
	void deleteWizardByIdTest(String wizardName) {

		when(elderService.deleteWizardById(wizardName)).thenReturn(Optional.ofNullable(wizardDTO));

		Optional<WizardDTO> resultado = elderService.deleteWizardById(wizardName);

		ResponseEntity<Optional<WizardDTO>> esperado = elderController.deleteWizardById(wizardName);

		assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

	}

	@ParameterizedTest
	@ValueSource(strings = {"FirstWizard","SecondWizard","ThirdWizard","FourthWizard"})
	void InvalidDeleteWizardByIdTest(String wizardName) {

		when(elderService.deleteWizardById(wizardName)).thenReturn(Optional.empty());

		Optional<WizardDTO> resultado = elderService.deleteWizardById(wizardName);

		ResponseEntity<Optional<WizardDTO>> esperado = elderController.deleteWizardById(wizardName);

		assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));

	}

	@Test
	void getAllWizardByIdTest() {

		when(elderService.getAllWizards()).thenReturn(new ArrayList<>());

		List<WizardDTO> resultado = elderService.getAllWizards();

		ResponseEntity<List<WizardDTO>> esperado = elderController.getAllWizards();

		assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.OK));

	}

	@Test
	void InvalidGetAllWizardTest() {

		when(elderService.getAllWizards()).thenReturn(new ArrayList<>());

		List<WizardDTO> resultado = elderService.getAllWizards();

		ResponseEntity<List<WizardDTO>> esperado = elderController.getAllWizards();

		assertThat(esperado).usingRecursiveComparison().isEqualTo(new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND));

	}

}

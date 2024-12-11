package es.tfg.codeguard.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.tfg.codeguard.controller.imp.ReportControllerImp;
import es.tfg.codeguard.model.dto.NewReportDTO;
import es.tfg.codeguard.model.dto.ReportDTO;
import es.tfg.codeguard.model.dto.TargetReportDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.exercisereport.ExerciseReportRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.service.ReportService;
import es.tfg.codeguard.service.imp.ReportServiceImp;
import es.tfg.codeguard.util.NotAllowedUserException;
import es.tfg.codeguard.util.ReportAlreadyExistException;
import es.tfg.codeguard.util.ReportNotFoundException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReportControllerTest {
	@Mock
	private UserPassRepository userPassRepository;
	@Mock
	private ExerciseReportRepository exerciseReportRepository;
	@Mock
	private JWTService jwtService;

	@Mock
	private ReportService reportService;
	@InjectMocks
	private ReportControllerImp reportControllerImp;
	@InjectMocks
	private ReportServiceImp reportServiceImp;
	/*
	 * ResponseEntity<UserDTO> result = userControllerImp.restoreUser(restoreDTO);
	 * 
	 * assertThat(new
	 * ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(
	 * result);
	 */

	@Test
	void getAllNotAllowedUserEx() {
		String token = "exampleToken";

		when(reportService.getAllReports(token)).thenThrow(NotAllowedUserException.class);

		ResponseEntity<List<ReportDTO>> result = reportControllerImp.getAllReport(token);
		assertThat(new ResponseEntity<>(HttpStatus.UNAUTHORIZED)).usingRecursiveComparison().isEqualTo(result);
	}

	void getAllAllowed() {
		String token = "exampleToken";

		List<ReportDTO> list = new ArrayList<ReportDTO>();
		when(reportService.getAllReports(token)).thenReturn(list);

		ResponseEntity<List<ReportDTO>> result = reportControllerImp.getAllReport(token);
		assertThat(new ResponseEntity<>(HttpStatus.OK)).usingRecursiveComparison().isEqualTo(result);
	}

	@Test
	void createExistingReportTest() {
		String token = "exampleToken";

		NewReportDTO newReportDTO = new NewReportDTO("Message");

		when(reportService.createReport(token, newReportDTO)).thenThrow(ReportAlreadyExistException.class);

		ResponseEntity<ReportDTO> result = reportControllerImp.newReport(token, newReportDTO);
		assertThat(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).usingRecursiveComparison().isEqualTo(result);

	}

	@Test
	void createReportTest() {
		String token = "exampleToken";

		NewReportDTO newReportDTO = new NewReportDTO("Message");
		ReportDTO reportDTO = new ReportDTO("desc", "adminResponse", "userName");
		when(reportService.createReport(token, newReportDTO)).thenReturn(reportDTO);

		ResponseEntity<ReportDTO> result = reportControllerImp.newReport(token, newReportDTO);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	void solveReportNotAllowedTest() {
		String userName = "exampleName";
		String token = "exampleToken";
		TargetReportDTO targetReportDTO = new TargetReportDTO(userName, "desc", "response");
		when(reportService.solveReport(token, targetReportDTO)).thenThrow(NotAllowedUserException.class);

		ResponseEntity<ReportDTO> result = reportControllerImp.solveReport(token, targetReportDTO);
		assertThat(new ResponseEntity<>(HttpStatus.UNAUTHORIZED)).usingRecursiveComparison().isEqualTo(result);

	}
	
	@Test
	void solveReportControllerNotFoundTest() {
		String userName = "exampleName";
		String token = "exampleToken";
		TargetReportDTO targetReportDTO = new TargetReportDTO(userName, "desc", "response");
		when(reportService.solveReport(token, targetReportDTO)).thenThrow(ReportNotFoundException.class);

		ResponseEntity<ReportDTO> result = reportControllerImp.solveReport(token, targetReportDTO);
		assertThat(new ResponseEntity<>(HttpStatus.NOT_FOUND)).usingRecursiveComparison().isEqualTo(result);

	}
	
	
	@Test
	void solveReportControllerTest() {
		String userName = "exampleName";
		String token = "exampleToken";
		ReportDTO reportDTO = new ReportDTO("desc", "adminResponse", "userName");
		TargetReportDTO targetReportDTO = new TargetReportDTO(userName, "desc", "response");
		when(reportService.solveReport(token, targetReportDTO)).thenReturn(reportDTO);

		ResponseEntity<ReportDTO> result = reportControllerImp.solveReport(token, targetReportDTO);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

	}
}

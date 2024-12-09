package es.tfg.codeguard.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import es.tfg.codeguard.model.dto.NewReportDTO;
import es.tfg.codeguard.model.dto.ReportDTO;
import es.tfg.codeguard.model.dto.TargetReportDTO;
import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.exercisereport.ExerciseReportRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.ReportServiceImp;
import es.tfg.codeguard.util.NotAllowedUserException;
import es.tfg.codeguard.util.ReportAlreadyExistException;
import es.tfg.codeguard.util.ReportNotFoundException;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
	@Mock
	private UserPassRepository userPassRepository;
	@Mock
	private ExerciseReportRepository exerciseReportRepository;
	@Mock
    private JWTService jwtService;
	
	@InjectMocks
	private ReportServiceImp reportServiceImp;
	
	
	@Test
	void getAllNotAuthorizedError() {
		String token="exampleToken";
		String userName="exampleUserName";
		UserPass pass=new UserPass(userName, "hashedPass", false);

		when(jwtService.extractUserPass(token)).thenReturn(pass);
		
		when(userPassRepository.findById(userName)).thenReturn(Optional.of(pass));
		

		assertThrows(NotAllowedUserException.class, ()->reportServiceImp.getAllReports(token));
	}
	
	
	@Test
	void getAllAuthorized() {
		String token="exampleToken";
		String userName="exampleUserName";
		UserPass pass=new UserPass(userName, "hashedPass", true);

		when(jwtService.extractUserPass(token)).thenReturn(pass);
		
		when(userPassRepository.findById(userName)).thenReturn(Optional.of(pass));
		

		assertDoesNotThrow(()->reportServiceImp.getAllReports(token));
	}
	
	@Test
	void createExistingReportTest() {
		String userName="exampleName";
		String token="exampleToken";
		UserPass pass=new UserPass(userName, "hashedPass", true);

		NewReportDTO newReportDTO=new NewReportDTO("Message");
		when(jwtService.extractUserPass(token)).thenReturn(pass);
		
		when(exerciseReportRepository.findByUserNameAndDescription(userName, newReportDTO.message())).thenReturn(Optional.of(new ExerciseReport()));


		assertThrows(ReportAlreadyExistException.class, ()->reportServiceImp.createReport(token, newReportDTO));
	}
	
	
	@Test
	void createReportTest() {
		
		String userName="exampleName";
		String token="exampleToken";
		UserPass pass=new UserPass(userName, "hashedPass", true);

		NewReportDTO newReportDTO=new NewReportDTO("Message");
		when(jwtService.extractUserPass(token)).thenReturn(pass);
		
		when(exerciseReportRepository.findByUserNameAndDescription(userName, newReportDTO.message())).thenReturn(Optional.empty());


		assertDoesNotThrow(()->reportServiceImp.createReport(token, newReportDTO));
	}
	
	@Test
	void solveReportTestNotAllowed() {
		String userName="exampleName";
		String token="exampleToken";
		UserPass pass=new UserPass(userName, "hashedPass", false);
		
		when(jwtService.extractUserPass(token)).thenReturn(pass);
		when(userPassRepository.findById(userName)).thenReturn(Optional.of(pass));
		
		assertThrows(NotAllowedUserException.class, ()->reportServiceImp.solveReport(token, new TargetReportDTO(userName, "desc", "response")));
	}
	
	@Test
	void solveReportTestReportNotFound() {
		String userName="exampleName";
		String token="exampleToken";
		UserPass pass=new UserPass(userName, "hashedPass", true);
		TargetReportDTO target=new TargetReportDTO(userName, "desc", "response");
		when(jwtService.extractUserPass(token)).thenReturn(pass);
		when(userPassRepository.findById(userName)).thenReturn(Optional.of(pass));
		
		when(exerciseReportRepository.findByUserNameAndDescription(target.userName(), target.description())).thenReturn(Optional.empty());
		
		assertThrows(ReportNotFoundException.class, ()->reportServiceImp.solveReport(token, new TargetReportDTO(userName, "desc", "response")));
	}
	
	@Test
	void solveReportTest() {
		String userName="exampleName";
		String token="exampleToken";
		UserPass pass=new UserPass(userName, "hashedPass", true);
		TargetReportDTO target=new TargetReportDTO(userName, "desc", "response");
		ExerciseReport report=new ExerciseReport();
		when(jwtService.extractUserPass(token)).thenReturn(pass);
		when(userPassRepository.findById(userName)).thenReturn(Optional.of(pass));
		
		when(exerciseReportRepository.findByUserNameAndDescription(target.userName(), target.description())).thenReturn(Optional.of(report));
		
		assertDoesNotThrow(()->reportServiceImp.solveReport(token, target));
	}
	
	
}

package es.tfg.codeguard.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.NewReportDTO;
import es.tfg.codeguard.model.dto.ReportDTO;
import es.tfg.codeguard.model.dto.TargetReportDTO;
import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.exercisereport.ExerciseReportRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.service.ReportService;
import es.tfg.codeguard.util.NotAllowedUserException;
import es.tfg.codeguard.util.ReportAlreadyExistException;
import es.tfg.codeguard.util.ReportNotFoundException;

@Service
public class ReportServiceImp implements ReportService{
	@Autowired
	private UserPassRepository userPassRepository;
	@Autowired
	private ExerciseReportRepository exerciseReportRepository;
	@Autowired
    private JWTService jwtService;

	@Override
	public List<ReportDTO> getAllReports(String token) {
		String userName= jwtService.extractUserPass(token).getUsername();
		UserPass user= userPassRepository.findById(userName).get();

		if(!user.isAdmin()) throw new NotAllowedUserException("Not Authorized action");//Admin?
		return exerciseReportRepository.findAll().stream()
				.map(exReport->new ReportDTO(exReport)).toList();
	}

	@Override
	public ReportDTO createReport(String token, NewReportDTO newReportDTO) {
		String userName= jwtService.extractUserPass(token).getUsername();
		if(exerciseReportRepository.findByUserNameAndDescription(userName, newReportDTO.message()).isPresent()) {
			throw new ReportAlreadyExistException("This report has been already created");
		}
		exerciseReportRepository.save(new ExerciseReport(newReportDTO.message(), userName));
		
		return new ReportDTO(newReportDTO.message(), "", userName);
	}

	@Override
	public ReportDTO solveReport(String token, TargetReportDTO target) {
		
		String userName=jwtService.extractUserPass(token).getUsername();
		UserPass user= userPassRepository.findById(userName).get();
		
		if(!user.isAdmin()) throw new NotAllowedUserException("Not Authorized action");//Admin?
		
		
		Optional<ExerciseReport> opRep=exerciseReportRepository.findByUserNameAndDescription(target.userName(), target.description());
		
		if(opRep.isEmpty()) {
			throw new ReportNotFoundException("Something goes wrong: \nreport ["+target+"] not found");
		}
		ExerciseReport rep= opRep.get();
		
		rep.setAdminName(userName);
		rep.setAdminResponse(target.adminResponse());
		rep.setResolved(true);
		
		exerciseReportRepository.save(rep);
		
		return new ReportDTO(rep);
	}

}

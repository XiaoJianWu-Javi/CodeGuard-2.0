package es.tfg.codeguard.controller.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.tfg.codeguard.controller.ReportController;
import es.tfg.codeguard.model.dto.NewReportDTO;
import es.tfg.codeguard.model.dto.ReportDTO;
import es.tfg.codeguard.model.dto.TargetReportDTO;
import es.tfg.codeguard.service.ReportService;
import es.tfg.codeguard.service.imp.ReportServiceImp;
import es.tfg.codeguard.util.NotAllowedUserException;
import es.tfg.codeguard.util.ReportAlreadyExistException;
import es.tfg.codeguard.util.ReportNotFoundException;

@RestController
public class ReportControllerImp implements ReportController{
	@Autowired
	private ReportService reportService;

	@Override
	public ResponseEntity<List<ReportDTO>> getAllReport(String userToken) {
		try {
			return new ResponseEntity<List<ReportDTO>>(reportService.getAllReports(userToken), HttpStatus.OK);
		}catch (NotAllowedUserException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public ResponseEntity<ReportDTO> newReport(String userToken, NewReportDTO newReportDTO) {
		try {
			return new ResponseEntity<ReportDTO>(reportService.createReport(userToken, newReportDTO), HttpStatus.OK);
		}catch (ReportAlreadyExistException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@Override
	public ResponseEntity<ReportDTO> solveReport(String userToken, TargetReportDTO targetReport) {
		try {
			return new ResponseEntity<ReportDTO>(reportService.solveReport(userToken, targetReport), HttpStatus.OK);
		}catch (NotAllowedUserException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}catch (ReportNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}

package es.tfg.codeguard.service;

import java.util.List;

import es.tfg.codeguard.model.dto.NewReportDTO;
import es.tfg.codeguard.model.dto.ReportDTO;
import es.tfg.codeguard.model.dto.TargetReportDTO;

public interface ReportService {

	public List<ReportDTO> getAllReports(String token);
	
	public ReportDTO createReport(String token, NewReportDTO newReportDTO);
	
	public ReportDTO solveReport(String token, TargetReportDTO traget);
	
	
}

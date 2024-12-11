package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.exercisereport.ExerciseReport;

public record ReportDTO(String userName, String adminName, String description, String adminResponse, boolean state) {

	public ReportDTO(ExerciseReport exerciseReport) {
		this(
		exerciseReport.getUserName(),
		exerciseReport.getAdminName(),
		exerciseReport.getDescription(),
		exerciseReport.getAdminResponse(),
		exerciseReport.isResolved()
		);
	}
	
	public ReportDTO(String description, String adminResponse, String userName) {
		this(userName, null ,description, adminResponse, false);
	}
	
	public ReportDTO(String description, String adminResponse, String userName, String adminName) {
		this(userName, adminName, description, adminResponse, false);
	}
}

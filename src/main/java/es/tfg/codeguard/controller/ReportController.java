package es.tfg.codeguard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.tfg.codeguard.model.dto.NewReportDTO;
import es.tfg.codeguard.model.dto.ReportDTO;
import es.tfg.codeguard.model.dto.TargetReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "http://localhost:4200")
public interface ReportController {

    @GetMapping("/getAll")
    @Operation(summary = "All Reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ReportDTO>> getAllReport(@RequestHeader("Authorization") String userToken);

	
    @PostMapping("/new")
    @Operation(summary = "New Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Report already exists"),

    })
    public ResponseEntity<ReportDTO> newReport(@RequestHeader("Authorization") String userToken, @RequestBody NewReportDTO newReportDTO);

    @PatchMapping("/solve")
    @Operation(summary = "Solve Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    public ResponseEntity<ReportDTO> solveReport(@RequestHeader("Authorization") String userToken, @RequestBody TargetReportDTO targetReport);

    
   
}
package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.dto.CompilerTestRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compiler")
@CrossOrigin(origins = "http://localhost:4200")
public interface CompilationController {

    @PostMapping("/compile")
    @Operation(summary = "Compile the code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compiled successfully"),
            @ApiResponse(responseCode = "400", description = "The class is not well written"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "408", description = "The exercise takes too much time to execute"),
            @ApiResponse(responseCode = "500", description = "The server could not process the exercise")
    })
    public ResponseEntity<CompilerResponseDTO> compileCode(@RequestHeader("Authorization") String userToken, @RequestBody CompilerRequestDTO compilerRequestDTO);

    @PostMapping("/test")
    @Operation(summary = "Compile the test code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compiled successfully"),
            @ApiResponse(responseCode = "400", description = "The class is not well written"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "408", description = "The exercise takes too much time to execute"),
            @ApiResponse(responseCode = "500", description = "The server could not process the exercise")
    })
    public ResponseEntity<CompilerResponseDTO> compileTestCode(@RequestHeader("Authorization") String userToken, @RequestBody CompilerTestRequestDTO compilerTestRequestDTO);
}

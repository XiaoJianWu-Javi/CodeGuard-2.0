package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
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
            @ApiResponse(responseCode = "400", description = "Can't compile"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<CompilerResponseDTO> compileCode(@RequestHeader("Authorization") String userToken, @RequestBody CompilerRequestDTO compilerRequestDTO);
}

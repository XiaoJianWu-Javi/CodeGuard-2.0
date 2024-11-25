package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.model.dto.CompilerTestRequestDTO;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.util.TestCasesNotFoundException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public interface CompilerService {

    public CompilerResponseDTO compileSolution(String userToken, CompilerRequestDTO compileInfo) throws ClassNotFoundException, IOException, CompilationErrorException, TimeoutException, InterruptedException, TestCasesNotFoundException;

    //TODO: tiene que guardar al final los test tambien en los ejercicios
    //TODO: hacer controlador para este endpoint
    public Optional<CompilerResponseDTO> compileTest(String userToken, CompilerTestRequestDTO compileInfo) throws ClassNotFoundException, IOException, CompilationErrorException, TimeoutException, InterruptedException, TestCasesNotFoundException;

}

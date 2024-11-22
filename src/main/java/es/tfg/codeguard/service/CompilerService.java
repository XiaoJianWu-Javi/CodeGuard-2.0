package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.CompilerRequestDTO;
import es.tfg.codeguard.model.dto.CompilerResponseDTO;
import es.tfg.codeguard.util.CompilationErrorException;
import es.tfg.codeguard.util.TestCasesNotFoundException;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public interface CompilerService {

    public Optional<CompilerResponseDTO> compile(String userToken, CompilerRequestDTO compileInfo) throws ClassNotFoundException, IOException, CompilationErrorException, TimeoutException, InterruptedException, TestCasesNotFoundException;

}

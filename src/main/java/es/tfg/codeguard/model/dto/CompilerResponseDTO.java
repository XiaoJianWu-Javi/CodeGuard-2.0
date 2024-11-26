package es.tfg.codeguard.model.dto;

public record CompilerResponseDTO(
        Integer exerciseCompilationCode,
        String exerciseCompilationMessage,
        Integer executionCode,
        String executionMessage
) {
}

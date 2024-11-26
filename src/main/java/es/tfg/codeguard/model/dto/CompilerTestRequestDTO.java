package es.tfg.codeguard.model.dto;

public record CompilerTestRequestDTO(String exerciseId,
                                     String exerciseSolution,
                                     String exerciseTests,
                                     String exercisePlaceHolder) {
}

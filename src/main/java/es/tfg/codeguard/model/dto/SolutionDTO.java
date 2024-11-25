package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.exercise.Exercise;

public record SolutionDTO(java.util.Map<String, String> solutions) {
	
	public SolutionDTO(Exercise exercise) {
		this(exercise.getSolutions());
	}
}

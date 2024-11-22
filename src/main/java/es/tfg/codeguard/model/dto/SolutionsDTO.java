package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.exercise.Exercise;

public record SolutionsDTO(String exerciseId, java.util.Map<String, String> solutions) {
	
	public SolutionsDTO(Exercise exercise) {
		this(exercise.getId(), exercise.getSolutions());
	}
}

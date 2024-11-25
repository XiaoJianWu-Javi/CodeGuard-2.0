package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.exercise.Exercise;

public record ExerciseDTO(String id, String title, String description, 
							String tester, String creator, String compilerClass, String placeholder) {

    public ExerciseDTO(Exercise exercise) {
        this(exercise.getId(), 
        		exercise.getTitle(), 
        		exercise.getDescription(), 
        		exercise.getTester(), 
        		exercise.getCreator(),
        		exercise.getCompilerClass(),
				exercise.getPlaceholder());
    }
    
    public ExerciseDTO(String id, String title, String description, String tester, String creator) {
    	this(id, title, description, tester, creator, "", "");
    }

}

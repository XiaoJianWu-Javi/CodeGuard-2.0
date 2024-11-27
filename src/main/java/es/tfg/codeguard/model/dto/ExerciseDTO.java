package es.tfg.codeguard.model.dto;

import es.tfg.codeguard.model.entity.exercise.Exercise;

public record ExerciseDTO(String id, String title, String description, 
							String tester, String creator, String placeholder,
							Integer solvedTimes) {

    public ExerciseDTO(Exercise exercise) {
        this(exercise.getId(), 
        		exercise.getTitle(), 
        		exercise.getDescription(), 
        		exercise.getTester(), 
        		exercise.getCreator(),
				exercise.getPlaceholder(),
				exercise.getSolvedTimes());
    }
    
    public ExerciseDTO(String id, String title, String description, String tester, String creator) {
    	this(id, title, description, tester, creator, "", 0);
    }

}

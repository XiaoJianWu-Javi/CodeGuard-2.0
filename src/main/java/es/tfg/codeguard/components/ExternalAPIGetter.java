package es.tfg.codeguard.components;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.service.ExternalAPIService;

@Component
public class ExternalAPIGetter {

    private final ExternalAPIService codeWarsAPIService;
    private final ExternalAPIService projectEulerAPIService;
    private final ExerciseRepository exerciseRepository;

    public ExternalAPIGetter(ExternalAPIService codeWarsAPIService, ExternalAPIService projectEulerAPIService,
            ExerciseRepository exerciseRepository) {

        this.codeWarsAPIService = codeWarsAPIService;
        this.projectEulerAPIService = projectEulerAPIService;
        this.exerciseRepository = exerciseRepository;
    }

    private static final int N_EXERCISES = 10;
    
    @Bean
    void getExercisesFromCodeWars() {

        exerciseRepository.save(new Exercise(codeWarsAPIService.requestExerciseById("valid-braces")));
        exerciseRepository.save(new Exercise(codeWarsAPIService.requestExerciseById("magic-music-box")));
    }

    @Bean
    void getNExercisesFromProjectEuler() {
        
        for (ExerciseDTO exercise : projectEulerAPIService.requestNExercises(N_EXERCISES))
            exerciseRepository.save(new Exercise(exercise));
    }
}

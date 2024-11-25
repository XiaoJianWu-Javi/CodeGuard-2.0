package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExerciseService {

    public Optional<ExerciseDTO> getExerciseById(String exerciseId);

    public List<ExerciseDTO> getAllExercises();
    
    public SolutionDTO getAllSolutionsForExercise(String exerciseId);

    public Optional<String> getTestFromExercise(String exerciseId);

}

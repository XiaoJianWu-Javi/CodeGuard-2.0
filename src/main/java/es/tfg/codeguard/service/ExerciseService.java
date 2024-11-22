package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionsDTO;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExerciseService {

    public ExerciseDTO getExerciseById(String exerciseId);

    public List<ExerciseDTO> getAllExercises();
    
    public SolutionsDTO getAllSolutionsForExercise(String exerciseId);

}

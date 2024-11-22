package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExerciseService {

    public Optional<ExerciseDTO> getExerciseById(String exerciseId);

    public List<ExerciseDTO> getAllExercises();

    public Optional<String> getTestFromExercise(String exerciseId);

}

package es.tfg.codeguard.service;

import java.util.List;
import java.util.Optional;

import es.tfg.codeguard.model.dto.CreateExerciseDTO;
import org.springframework.stereotype.Service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;

@Service
public interface ExerciseService {

    public ExerciseDTO createExercise(String userToken, CreateExerciseDTO createExerciseDTO);

    public ExerciseDTO getExerciseById(String exerciseId);

    public List<ExerciseDTO> getAllExercisesPaginated(String search, Integer page, boolean desc);

    public List<ExerciseDTO> getAllExercises();

    public List<SolutionDTO> getAllSolutionsForExercise(String exerciseId);

    public SolutionDTO getUserSolutionForExercise(String username, String exerciseId);

    public Optional<String> getTestFromExercise(String exerciseId);

    public void addSolutionToExercise(SolutionDTO solution);

    public void addTestToExercise(SolutionDTO solution, String test, String placeholder);
}

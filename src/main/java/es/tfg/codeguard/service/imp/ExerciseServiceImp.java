package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.util.ExerciseNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImp implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public Optional<ExerciseDTO> getExerciseById(String exerciseId) {

        if (exerciseRepository.findById(exerciseId).isEmpty()) {
        	return Optional.empty();
        }

        return exerciseRepository.findById(exerciseId).map(ExerciseDTO::new);

    }

    @Override
    public List<ExerciseDTO> getAllExercises() {

        return exerciseRepository.findAll().stream().map(ExerciseDTO::new).toList();

    }

    }

    @Override
    public Optional<String> getTestFromExercise(String exerciseId) {

        if (exerciseRepository.findById(exerciseId).isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(exerciseRepository.findById(exerciseId).get().getTest());
    }
	@Override
	public SolutionDTO getAllSolutionsForExercise(String exerciseId) {

		if (exerciseRepository.findById(exerciseId).isEmpty()) {
        	throw new ExerciseNotFoundException(exerciseId);
        }

		return exerciseRepository.findById(exerciseId).map(SolutionDTO::new).get();
	}
}

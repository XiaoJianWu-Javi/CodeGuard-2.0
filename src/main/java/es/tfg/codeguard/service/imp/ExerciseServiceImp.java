package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
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
    public ExerciseDTO getExerciseById(String exerciseId) {

        Optional<Exercise> exerciseOptional = exerciseRepository.findById(exerciseId);

        if (exerciseOptional.isEmpty()) {
            throw new ExerciseNotFoundException("Exercise not found [" +exerciseId +"]");
        }

        return new ExerciseDTO(exerciseOptional.get());

    }

    @Override
    public List<ExerciseDTO> getAllExercises() {

        return exerciseRepository.findAll().stream().map(ExerciseDTO::new).toList();

    }
}

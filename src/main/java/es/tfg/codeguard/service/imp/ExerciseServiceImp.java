package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImp implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public Optional<ExerciseDTO> getExerciseById(int exerciseId) {

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

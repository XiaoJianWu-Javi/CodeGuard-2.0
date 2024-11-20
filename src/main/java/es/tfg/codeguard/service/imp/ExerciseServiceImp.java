package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.entity.Exercise;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.service.ExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImp implements ExerciseService {

    private ExerciseRepository exerciseRepository;

    public ExerciseServiceImp(ExerciseRepository exerciseRepository){
        this.exerciseRepository=exerciseRepository;
    }

    //TODO: COMPLETE THE SERVICES
    @Override
    public Optional<ExerciseDTO> getExerciseByName(String exerciseName) {

        if(exerciseRepository.findById(exerciseName).isEmpty()){
            return Optional.empty();
        }

        //return exerciseRepository.findById(exerciseName).map(ExerciseDTO::new);

        return null;

    }

    @Override
    public List<ExerciseDTO> getAllExercises() {

        //return exerciseRepository.findAll().stream().map(ExerciseDTO::new).toList();

        return null;
    }
}

package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.ExerciseController;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
public class ExerciseControllerImp implements ExerciseController {
    @Override
    public ResponseEntity<ExerciseDTO> createExercise() {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseDTO> getExercise() {
        return null;
    }

    @Override
    public ResponseEntity<ExerciseDTO> getAllExercises() {
        return null;
    }
}

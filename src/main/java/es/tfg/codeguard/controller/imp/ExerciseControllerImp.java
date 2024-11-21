package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.ExerciseController;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseControllerImp implements ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseControllerImp(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Override
    public ResponseEntity<ExerciseDTO> getExercise(String exerciseId) {

        return exerciseService
                .getExerciseById(exerciseId)
                .map(exerciseDTO -> new ResponseEntity<>(exerciseDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {

        return new ResponseEntity<>(exerciseService.getAllExercises(), HttpStatus.OK);

    }
}

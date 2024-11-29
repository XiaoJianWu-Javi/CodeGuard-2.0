package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.ExerciseController;
import es.tfg.codeguard.model.dto.CreateExerciseDTO;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.geom.NoninvertibleTransformException;
import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseControllerImp implements ExerciseController {

    private ExerciseService exerciseService;

    public ExerciseControllerImp(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @Override
    public ResponseEntity<ExerciseDTO> createExercise(String userToken, CreateExerciseDTO createExerciseDTO) {
        try {
            return ResponseEntity.ok(exerciseService.createExercise(userToken, createExerciseDTO));
        } catch (ExerciseAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (ExerciseTitleNotValidException | ExerciseDescriptionNotValid e) {
            return ResponseEntity.badRequest().build();
        } catch (NotAllowedUserException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @Override
    public ResponseEntity<ExerciseDTO> getExercise(String exerciseId) {

        try {
            return ResponseEntity.ok(exerciseService.getExerciseById(exerciseId));
        } catch (ExerciseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<List<ExerciseDTO>> getAllExercisesPaginated(String search, Integer page, boolean desc) {
        return ResponseEntity.ok(exerciseService.getAllExercisesPaginated(search, page, desc));
    }

    @Override
    public ResponseEntity<List<SolutionDTO>> getAllSolutionsForExercise(String exerciseId) {

        try {
            return new ResponseEntity<>(exerciseService.getAllSolutionsForExercise(exerciseId), HttpStatus.OK);
        } catch (ExerciseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<SolutionDTO> getUserSolutionForExercise(String username, String exerciseId) {

        try {
            return new ResponseEntity<>(exerciseService.getUserSolutionForExercise(username, exerciseId), HttpStatus.OK);
        } catch (ExerciseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }
}

package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public interface ExerciseController {


//    @GetMapping("createExercise")
//    @Operation(summary = "Create a new exercise from external API")
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "New exercise create"),
//            @ApiResponse(responseCode = "409", description = "Exercise created yet"),
//            @ApiResponse(responseCode = "400", description = "Exercise name not valid")
//    })
//    public ResponseEntity<ExerciseDTO> createExercise();


    @GetMapping("/{exerciseId}")
    @Operation(summary = "Get exercise by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exercise found"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable String exerciseId);

    @GetMapping("/allExercise")
    @Operation(summary = "Get all exercises")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Exercises found")
    })
    public ResponseEntity<List<ExerciseDTO>> getAllExercises();


}

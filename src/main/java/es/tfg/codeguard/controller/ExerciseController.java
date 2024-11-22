package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise found"),
            @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable String exerciseId);

    @GetMapping("/allExercise")
    @Operation(summary = "Get all exercises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercises found")
    })
    public ResponseEntity<List<ExerciseDTO>> getAllExercises();

    @GetMapping("/{exerciseId}/allSolutions")
    @Operation(summary = "Get all solutions for a given exercise")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Solutions found"),
    		@ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<SolutionDTO> getAllSolutionsForExercise(@PathVariable String exerciseId);
}

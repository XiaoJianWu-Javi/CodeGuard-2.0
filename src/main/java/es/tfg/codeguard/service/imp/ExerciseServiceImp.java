package es.tfg.codeguard.service.imp;

import es.tfg.codeguard.model.dto.CreateExerciseDTO;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.service.ExerciseService;
import es.tfg.codeguard.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImp implements ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTServiceImp jwtServiceImp;

    @Override
    public ExerciseDTO createExercise(String userToken, CreateExerciseDTO createExerciseDTO) {

        if (!isValidTitle(createExerciseDTO.title())) {
            throw new ExerciseTitleNotValidException("Exercise title no valid [ " + createExerciseDTO.title() + " ]");
        }

        if (!isValidDescription(createExerciseDTO.description())) {
            throw new ExerciseDescriptionNotValid("Exercise description not valid [ " + createExerciseDTO.description() + " ]");
        }

        Optional<Exercise> exerciseOptional = exerciseRepository.findById(getIdFromTitle(createExerciseDTO.title()));

        if (exerciseOptional.isPresent()) {
            throw new ExerciseAlreadyExistException("Exercise already exist [ " + createExerciseDTO.title() + " ]");
        }

        String creatorUser = jwtServiceImp.extractUserPass(userToken).getUsername();

        User user = userRepository.findById(creatorUser).get();

        if (!user.isCreator()) {
            throw new NotAllowedUserException(user.getUsername());
        }

        ExerciseDTO exerciseDTO = new ExerciseDTO(getIdFromTitle(createExerciseDTO.title()), createExerciseDTO.title(), createExerciseDTO.description(), null, creatorUser);

        exerciseRepository.save(new Exercise(exerciseDTO));

        return exerciseDTO;

    }

    @Override
    public ExerciseDTO getExerciseById(String exerciseId) {

        Optional<Exercise> exerciseOptional = exerciseRepository.findById(exerciseId);

        if (exerciseOptional.isEmpty()) {
            throw new ExerciseNotFoundException("Exercise not found [" + exerciseId + "]");
        }

        return new ExerciseDTO(exerciseOptional.get());

    }

    @Override
    public List<ExerciseDTO> getAllExercisesPaginated(String search, Integer page, boolean desc) {

        if (desc) {
            Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "title"));
            return exerciseRepository.findByTitleContaining(search, pageable).stream().map(ExerciseDTO::new).toList();
        }

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "title"));
        return exerciseRepository.findByTitleContaining(search, pageable).stream().map(ExerciseDTO::new).toList();

    }

    @Override
    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream().map(ExerciseDTO::new).toList();
    }

    @Override
    public List<SolutionDTO> getAllSolutionsForExercise(String exerciseId) {

        if (exerciseRepository.findById(exerciseId).isEmpty()) {
            throw new ExerciseNotFoundException(exerciseId);
        }
        return exerciseRepository.findById(exerciseId).get().getSolutions().entrySet()
                .stream()
                .map(solution -> new SolutionDTO(exerciseId, solution.getKey(), solution.getValue()))
                .toList();
    }

    @Override
    public SolutionDTO getUserSolutionForExercise(String username, String exerciseId) {

        return getAllSolutionsForExercise(exerciseId).stream()
                .filter(solution -> solution.username().equals(username))
                .toList().getFirst();
    }

    @Override
    public Optional<String> getTestFromExercise(String exerciseId) {

        if (exerciseRepository.findById(exerciseId).isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(exerciseRepository.findById(exerciseId).get().getTest());
    }

    @Override
    public void addSolutionToExercise(SolutionDTO solution) {

        if (exerciseRepository.findById(solution.exerciseId()).isEmpty()) {
            throw new ExerciseNotFoundException(solution.exerciseId());
        }
        if (userRepository.findById(solution.username()).isEmpty()) {
            throw new UserNotFoundException(solution.username());
        }

        Exercise exercise = exerciseRepository.findById(solution.exerciseId()).get();
        exercise.addSolution(solution.username(), solution.solution());

        User user = userRepository.findById(solution.username()).get();
        user.addExercise(solution.exerciseId());

        userRepository.save(user);
        exerciseRepository.save(exercise);
    }

    @Override
    public void addTestToExercise(SolutionDTO solution, String test, String placeholder) {

        if (exerciseRepository.findById(solution.exerciseId()).isEmpty()) {
            throw new ExerciseNotFoundException(solution.exerciseId());
        }

        Exercise exercise = exerciseRepository.findById(solution.exerciseId()).get();
        exercise.addSolution(solution.username(), solution.solution());
        exercise.setTest(test);
        exercise.setTester(solution.username());
        exercise.setPlaceholder(placeholder);

        exerciseRepository.save(exercise);
    }

    @Override
    public void addTriedUsernameToExercise(String exerciseId, String username) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new ExerciseNotFoundException("Exercise not found [ " + exerciseId + " ]"));

        exercise.addTriedUsername(username);

        exerciseRepository.save(exercise);
    }

    @Override
    public void addSolvedUsernameToExercise(String exerciseId, String username) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(
                () -> new ExerciseNotFoundException("Exercise not found [ " + exerciseId + " ]"));

        exercise.addSolvedUsername(username);

        exerciseRepository.save(exercise);
    }

    private String getIdFromTitle(String title) {
        return title.toLowerCase().replaceAll(" ", "-");
    }

    private boolean isValidTitle(String title) {
        return title != null && !title.isBlank();
    }

    private boolean isValidDescription(String description) {
        return description != null && !description.isBlank();
    }

}
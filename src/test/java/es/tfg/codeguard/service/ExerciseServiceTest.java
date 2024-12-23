package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.CreateExerciseDTO;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.SolutionDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.ExerciseServiceImp;
import es.tfg.codeguard.service.imp.JWTServiceImp;
import es.tfg.codeguard.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;

    @Mock
    private JWTServiceImp jwtServiceImp;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ExerciseServiceImp exerciseServiceImp;

    @Test
    public void getAllExercisesTest() {

        Exercise exercise1 = new Exercise();
        exercise1.setTitle("tittle1");
        exercise1.setDescription("description1");
        exercise1.setTest("tester1");
        exercise1.setCreator("creator1");
        exercise1.setSolutions(Map.of());

        Exercise exercise2 = new Exercise();
        exercise2.setTitle("tittle2");
        exercise2.setDescription("description2");
        exercise2.setTest("tester2");
        exercise2.setCreator("creator2");
        exercise2.setSolutions(Map.of());

        Exercise exercise3 = new Exercise();
        exercise3.setTitle("tittle3");
        exercise3.setDescription("description3");
        exercise3.setTest("tester3");
        exercise3.setCreator("creator3");
        exercise3.setSolutions(Map.of());

        List<Exercise> expectedExercises = List.of(exercise1, exercise2, exercise3);

        when(exerciseRepository.findAll()).thenReturn(expectedExercises);

        List<ExerciseDTO> resultExercises = exerciseServiceImp.getAllExercises();

        assertThat(expectedExercises.stream().map(ExerciseDTO::new)).usingRecursiveComparison().isEqualTo(resultExercises);

    }

    @Test
    public void getAallExercisesPaginated() {

        Exercise exercise1 = new Exercise();
        exercise1.setId("project-euler-9");
        exercise1.setTitle("Project Euler 9");
        exercise1.setDescription("description1");
        exercise1.setTest("tester1");
        exercise1.setCreator("creator1");
        exercise1.setSolutions(Map.of());

        Exercise exercise2 = new Exercise();
        exercise2.setId("saruman-123");
        exercise2.setTitle("La Traición de Isengard");
        exercise2.setDescription("description2");
        exercise2.setTest("tester2");
        exercise2.setCreator("creator2");
        exercise2.setSolutions(Map.of());

        Exercise exercise3 = new Exercise();
        exercise3.setId("magic-music-box");
        exercise3.setTitle("Magic Music Box");
        exercise3.setDescription("description3");
        exercise3.setTest("tester3");
        exercise3.setCreator("creator3");
        exercise3.setSolutions(Map.of());


        List<Exercise> expectedExercises = List.of(exercise1, exercise2, exercise3);

        when(exerciseRepository.findByTitleContaining("Je", PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "title")))).thenReturn(new PageImpl<>(List.of()));

        List<ExerciseDTO> exercises = exerciseServiceImp.getAllExercisesPaginated("Je", 0, false);
        List<ExerciseDTO> resultExercises = exerciseServiceImp.getAllExercisesPaginated("Je", 0, false);

        assertThat(List.of()).usingRecursiveComparison().isEqualTo(resultExercises);


        expectedExercises = List.of(exercise1);

        when(exerciseRepository.findByTitleContaining("je", PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "title")))).thenReturn(new PageImpl<>(expectedExercises));
        when(exerciseRepository.findByTitleContaining("je", PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "title")))).thenReturn(new PageImpl<>(expectedExercises));

        resultExercises = exerciseServiceImp.getAllExercisesPaginated("je", 1, false);

        assertThat(expectedExercises.stream().map(ExerciseDTO::new)).usingRecursiveComparison().isEqualTo(resultExercises);


        expectedExercises = List.of(exercise2, exercise3);

        when(exerciseRepository.findByTitleContaining("g", PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "title")))).thenReturn(new PageImpl<>(expectedExercises));

        resultExercises = exerciseServiceImp.getAllExercisesPaginated("g", 0, false);

        assertThat(expectedExercises.stream().map(ExerciseDTO::new)).usingRecursiveComparison().isEqualTo(resultExercises);

    }

    @Test
    public void getAllExercisesTestExercisesEmpty() {

        List<Exercise> exercisesExpected = Collections.emptyList();

        when(exerciseRepository.findAll()).thenReturn(exercisesExpected);

        List<ExerciseDTO> exercises = exerciseServiceImp.getAllExercises();

        assertThat(exercisesExpected.stream().map(ExerciseDTO::new)).usingRecursiveComparison().isEqualTo(exercises);

    }

    @ParameterizedTest
    @ValueSource(strings = {"exercise-99", "exercise-100", "exercise-101"})
    public void getExerciseByIdTestExerciseNotFound(String exerciseId) {

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseServiceImp.getExerciseById(exerciseId));

    }

    @ParameterizedTest
    @CsvSource({
            "tittle-1,First description,Gandalf,Saruman",
            "tittle-2,Second description,Saruman,Saruman",
            "tittle-2,Third description,Houdini,Gandalf",
    })
    public void getExerciseByIdTest(String exerciseId, String exerciseDescription, String tester, String creator) {

        Exercise exercise = new Exercise();
        exercise.setTitle(exerciseId);
        exercise.setDescription(exerciseDescription);
        exercise.setTest(tester);
        exercise.setCreator(creator);
        exercise.setSolutions(Map.of());

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        ExerciseDTO expectedExercise = new ExerciseDTO(exercise);
        ExerciseDTO actualExercise = exerciseServiceImp.getExerciseById(exerciseId);

        assertThat(expectedExercise).usingRecursiveComparison().isEqualTo(actualExercise);

    }

    @Test
    public void getAllSolutionsFromExercise() {

        java.util.Map<String, String> solutions = new java.util.HashMap<String, String>() {{
            put("user", "solution");
            put("user2", "solution");
        }};

        Exercise exercise = new Exercise("1", "titulo", "desc");
        exercise.setSolutions(solutions);

        List<SolutionDTO> expectedSolutions = solutions.entrySet().stream()
                .map(entry -> new SolutionDTO("1", entry.getKey(), entry.getValue()))
                .toList();

        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));

        List<SolutionDTO> actualSolutions = exerciseServiceImp.getAllSolutionsForExercise("1");

        assertArrayEquals(expectedSolutions.toArray(), actualSolutions.toArray());

    }

    @Test
    public void getAllSolutionsForExerciseTestEmptySolutions() {

        Exercise exercise = new Exercise("1", "titulo", "desc");

        List<Exercise> expectedSolutions = Collections.emptyList();

        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));

        List<SolutionDTO> actualSolutions = exerciseServiceImp.getAllSolutionsForExercise("1");

        assertArrayEquals(expectedSolutions.toArray(), actualSolutions.toArray());

    }

    @Test
    public void getUserSolutionForExerciseTest() {

        java.util.Map<String, String> solutions = new java.util.HashMap<String, String>() {{
            put("user", "solution");
            put("user2", "solution");
        }};

        Exercise exercise = new Exercise("1", "titulo", "desc");
        exercise.setSolutions(solutions);

        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exercise));

        SolutionDTO expectedSolution = new SolutionDTO(exercise.getId(), "user2", solutions.get("user2"));

        assertThat(expectedSolution).usingRecursiveComparison().isEqualTo(exerciseServiceImp.getUserSolutionForExercise("user2", "1"));

    }

    @Test
    public void getUserSolutionsForExerciseTestExerciseNotFound() {
        Exercise exercise = new Exercise("1", "titulo", "desc");
        when(exerciseRepository.findById("1")).thenReturn(Optional.empty());
        when(exerciseRepository.findById("2")).thenReturn(Optional.of(exercise));

        assertThrows(ExerciseNotFoundException.class, () -> exerciseServiceImp.getUserSolutionForExercise("user", "1"));
        assertThrows(NoSuchElementException.class, () -> exerciseServiceImp.getUserSolutionForExercise("user", "2"));

    }

    @Test
    public void getTestFromExerciseEmpty() {
        when(exerciseRepository.findById("1")).thenReturn(Optional.empty());

        Optional<String> tests = exerciseServiceImp.getTestFromExercise("1");

        assertThat(tests).isEmpty();
    }

    @Test
    public void getTestFromExercise() {

        Exercise exerciseExpected = new Exercise();
        exerciseExpected.setTitle("tittle1");
        exerciseExpected.setDescription("description1");
        exerciseExpected.setTest("tester1");
        exerciseExpected.setCreator("creator1");
        String expectedTests = "import org.junit.jupiter.api.Test;" +
                "public TestTest{ @Test void test(){}}";
        exerciseExpected.setTest(expectedTests);

        when(exerciseRepository.findById("1")).thenReturn(Optional.of(exerciseExpected));

        Optional<String> tests = exerciseServiceImp.getTestFromExercise("1");

        assertThat(expectedTests).isEqualTo(tests.get());
    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w\",Magic Music Box,\"The Magic Music Box exercise involves simulating a music box\",magic-music-box",
            "Rachel,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA\",Build Square,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",build-square",
            "Blaise,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw\",Delta Bits,\"Complete the function to determine the number of bits required to convert integer A to integer B (where A and B >= 0)\",delta-bits",
            "Albus,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk\",Reverse words,\"Complete the function that accepts a string parameter and reverses each word in the string\",reverse-words"

    })
    void createExerciseServiceTest(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(jwtServiceImp.extractUserPass(userToken)).thenReturn(new UserPass(username, "$2a$10$fixedEncodedPassword", false));
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());
        when(userRepository.findById(username)).thenReturn(Optional.of(new User(username, true, true)));

        ExerciseDTO expectedExerciseDTO = new ExerciseDTO(exerciseId, exerciseTitle, exerciseDescription, null, username);

        ExerciseDTO result = exerciseServiceImp.createExercise(userToken, createExerciseDTO);

        assertThat(expectedExerciseDTO).usingRecursiveComparison().isEqualTo(result);

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w\",Magic Music Box,\"The Magic Music Box exercise involves simulating a music box\",magic-music-box",
            "Rachel,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA\",Build Square,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",build-square",
            "Blaise,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw\",Delta Bits,\"Complete the function to determine the number of bits required to convert integer A to integer B (where A and B >= 0)\",delta-bits",
            "Albus,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk\",Reverse words,\"Complete the function that accepts a string parameter and reverses each word in the string\",reverse-words"
    })
    void createExerciseTestExerciseAlreadyExist(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(new Exercise(exerciseId, exerciseTitle, exerciseDescription)));

        assertThrows(ExerciseAlreadyExistException.class, () -> exerciseServiceImp.createExercise(userToken, createExerciseDTO));

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w\",,\"The Magic Music Box exercise involves simulating a music box\",magic-music-box",
            "Rachel,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA\",,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",build-square",
            "Blaise,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw\",,\"Complete the function to determine the number of bits required to convert integer A to integer B (where A and B >= 0)\",delta-bits",
            "Albus,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk\",,\"Complete the function that accepts a string parameter and reverses each word in the string\",reverse-words"
    })
    void createExerciseTestTitleNotValid(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        assertThrows(ExerciseTitleNotValidException.class, () -> exerciseServiceImp.createExercise(userToken, createExerciseDTO));

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w\",Magic Music Box,,magic-music-box",
            "Rachel,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA\",Build Square,,build-square",
            "Blaise,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw\",Delta Bits,,delta-bits",
            "Albus,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk\",Reverse words,,reverse-words"
    })
    void createExerciseTestExerciseDescriptionNotValid(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);
        assertThrows(ExerciseDescriptionNotValid.class, () -> exerciseServiceImp.createExercise(userToken, createExerciseDTO));

    }

    @ParameterizedTest
    @CsvSource({
            "Houdini,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJIb3VkaW5pIiwiaWF0IjoxNzMyNzc3OTkzLCJleHAiOjE3MzI4NjQzOTN9.BIOCXkd2KpiwBbaiv1lOt-gs5oDFQMF7dmei7MuWw4w\",Magic Music Box,\"The Magic Music Box exercise involves simulating a music box\",magic-music-box",
            "Rachel,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJSYWNoZWwiLCJpYXQiOjE3MzI3Nzg1NTEsImV4cCI6MTczMjg2NDk1MX0.Q-hQgnLCHAeLiG2mAjnaXb5ftQLfmis75jbvbLBFMlA\",Build Square,\"I will give you an integer. Give me back a shape that is as long and wide as the integer\",build-square",
            "Blaise,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJCbGFpc2UiLCJpYXQiOjE3MzI3Nzg5OTgsImV4cCI6MTczMjg2NTM5OH0.B2ueU8_beww-gYbobXn2aqdcDvDdxb6xcIsEwuSyEFw\",Delta Bits,\"Complete the function to determine the number of bits required to convert integer A to integer B (where A and B >= 0)\",delta-bits",
            "Albus,\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJBbGJ1cyIsImlhdCI6MTczMjc3OTE3NywiZXhwIjoxNzMyODY1NTc3fQ.9jTa2oHY9-KQlwXzBIIydCaIkimjDBZzomGIPlryYqk\",Reverse words,\"Complete the function that accepts a string parameter and reverses each word in the string\",reverse-words"

    })
    void createExerciseTestNotAllowedUser(String username, String userToken, String exerciseTitle, String exerciseDescription, String exerciseId) {
        CreateExerciseDTO createExerciseDTO = new CreateExerciseDTO(exerciseTitle, exerciseDescription);

        String hashedPass = new BCryptPasswordEncoder().encode("1234");

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        when(jwtServiceImp.extractUserPass(userToken)).thenReturn(new UserPass(username, hashedPass, false));

        when(userRepository.findById(username)).thenReturn(Optional.of(new User(username, false, false)));

        assertThrows(NotAllowedUserException.class, () -> exerciseServiceImp.createExercise(userToken, createExerciseDTO));

    }

}
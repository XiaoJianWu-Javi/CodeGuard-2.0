package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.ExerciseServiceImp;
import es.tfg.codeguard.service.imp.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;
    @InjectMocks
    private ExerciseServiceImp exerciseServiceImp;

    @Test
    public void TestFineGetAllExercises(){

        Exercise exercise1 = new Exercise();
        exercise1.setTitle("tittle1");
        exercise1.setDescription("description1");
        exercise1.setTest("tester1");
        exercise1.setCreator("creator1");

        Exercise exercise2 = new Exercise();
        exercise2.setTitle("tittle2");
        exercise2.setDescription("description2");
        exercise2.setTest("tester2");
        exercise2.setCreator("creator2");

        Exercise exercise3 = new Exercise();
        exercise3.setTitle("tittle3");
        exercise3.setDescription("description3");
        exercise3.setTest("tester3");
        exercise3.setCreator("creator3");


        List<Exercise> exercisesExpected = List.of(exercise1, exercise2, exercise3);

        when(exerciseRepository.findAll()).thenReturn(exercisesExpected);

        List<Exercise> exercises = exerciseRepository.findAll();

        assertThat(exercisesExpected).usingRecursiveComparison().isEqualTo(exercises);

    }

    @Test
    public void TestFailGetAllUsers(){


        List<Exercise> exercisesExpected = Collections.emptyList();

        when(exerciseRepository.findAll()).thenReturn(exercisesExpected);

        List<Exercise> exercises = exerciseRepository.findAll();

        assertThat(exercisesExpected).usingRecursiveComparison().isEqualTo(exercises);

    }

    @Test
    public void TestFailGetUserByName(){
        when(exerciseRepository.findById(1)).thenReturn(Optional.empty());

        Optional<ExerciseDTO> exercise = exerciseServiceImp.getExerciseById(1);

        assertThat(exercise).isEmpty();
    }

    @Test
    public void TestFineGetUserByName(){

        Exercise exerciseExpected = new Exercise();
        exerciseExpected.setTitle("tittle1");
        exerciseExpected.setDescription("description1");
        exerciseExpected.setTest("tester1");
        exerciseExpected.setCreator("creator1");

        when(exerciseRepository.findById(1)).thenReturn(Optional.of(exerciseExpected));

        Optional<Exercise> exercise = exerciseRepository.findById(1);

        assertThat(Optional.of(exerciseExpected)).usingRecursiveComparison().isEqualTo(exercise);
    }

}

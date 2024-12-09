package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserPrivilegesDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.imp.AdminServiceImp;
import es.tfg.codeguard.util.ExerciceNotFoundException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdminServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPassRepository userPassRepository;

    @Mock
    private DeletedUserRepository deletedUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private AdminServiceImp adminServiceImp;

    private UserPass userPass;

    private User user;

    private DeletedUser deletedUser;

    @BeforeEach
    void setUp() {

        when(passwordEncoder.encode("cantpass")).thenReturn(new BCryptPasswordEncoder().encode("cantpass"));

        userPass = new UserPass();
        userPass.setUsername("Gandalf");
        userPass.setAdmin(false);
        userPass.setHashedPass(passwordEncoder.encode("cantpass"));

        user = new User("Gandalf");

        deletedUser = new DeletedUser(user);
    }

    @Test
    void adminDeleteUserTestUserNotFound() {

        when(userRepository.findById("i2udgiqdqi????!=¿!02'31")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.deleteUser("i2udgiqdqi????!=¿!02'31"));

    }

    @Test
    void adminDeleteUserTest() {
        Optional<UserPass> userPassOpt = Optional.of(userPass);

        Optional<User> userOpt = Optional.of(user);
        when(userRepository.findById("Gandalf")).thenReturn(userOpt);

        UserDTO resultDeleteUser = adminServiceImp.deleteUser("Gandalf");

        DeletedUser expectedDeleteUser = deletedUser;

        assertThat(expectedDeleteUser).usingRecursiveComparison().isEqualTo(resultDeleteUser);

    }

    @ParameterizedTest
    @CsvSource({
            "G4ndalf,cantpass",
            "D3xter,newSecurePassword12",
            "P0l,9876secure"
    })
    void adminChangeUserPasswordTestUserNotFound(String username, String newPassword) {
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.updatePassword(username, newPassword));

    }

    @Test
    void adminChangeUserPasswordTest() {

        UserPassDTO userExpected = new UserPassDTO("Gandalf", false);

        when(userRepository.findById("Gandalf")).thenReturn(Optional.of(user));
        when(userPassRepository.findById("Gandalf")).thenReturn(Optional.of(userPass));

        UserPassDTO userOptional = adminServiceImp.updatePassword("Gandalf", "cantpass");

        assertThat(userExpected).usingRecursiveComparison().isEqualTo(userOptional);
    }

    @Test
    void adminUpdateUserPrivilegesTest() {

        UserPrivilegesDTO userPrivilegesDTO1 = new UserPrivilegesDTO("theBestMagician", false, true);

        UserDTO expectedUser = new UserDTO(userPrivilegesDTO1.username(), userPrivilegesDTO1.tester(), userPrivilegesDTO1.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO1.username())).thenReturn(Optional.of(new User(userPrivilegesDTO1.username(), false, false)));

        UserDTO resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO1);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);


        UserPrivilegesDTO userPrivilegesDTO2 = new UserPrivilegesDTO("MMM4gic", true, false);

        expectedUser = new UserDTO(userPrivilegesDTO2.username(), userPrivilegesDTO2.tester(), userPrivilegesDTO2.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO2.username())).thenReturn(Optional.of(new User(userPrivilegesDTO2.username(), false, false)));

        resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO2);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);


        UserPrivilegesDTO userPrivilegesDTO3 = new UserPrivilegesDTO("SSS0ulD3v", true, true);

        expectedUser = new UserDTO(userPrivilegesDTO3.username(), userPrivilegesDTO3.tester(), userPrivilegesDTO3.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO3.username())).thenReturn(Optional.of(new User(userPrivilegesDTO3.username(), false, false)));

        resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO3);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);


        UserPrivilegesDTO userPrivilegesDTO4 = new UserPrivilegesDTO("Noo0bDev", false, false);

        expectedUser = new UserDTO(userPrivilegesDTO4.username(), userPrivilegesDTO4.tester(), userPrivilegesDTO4.creator(), List.of());

        when(userRepository.findById(userPrivilegesDTO4.username())).thenReturn(Optional.of(new User(userPrivilegesDTO4.username(), false, false)));

        resultUser = adminServiceImp.updateUserPrivileges(userPrivilegesDTO4);

        assertThat(expectedUser).usingRecursiveComparison().isEqualTo(resultUser);

    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º"})
    void adminUpdateUserPrivilegesTest(String username) {

        UserPrivilegesDTO userPrivilegesDTO1 = new UserPrivilegesDTO("username", true, true);

        when(userRepository.findById(userPrivilegesDTO1.username())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> adminServiceImp.updateUserPrivileges(userPrivilegesDTO1));

    }

    @Test
    void adminUpdateTestForExerciseTest() {
        Exercise exercise = new Exercise();
        exercise.setId("project-euler-9");
        exercise.setTitle("Project Euler 9");
        exercise.setDescription("description1");
        exercise.setTest("test");
        exercise.setCreator("creator1");
        exercise.setSolutions(Map.of());

        String newTest = "newTest";

        Exercise expectedExercise = new Exercise();
        expectedExercise.setId("project-euler-9");
        expectedExercise.setTitle("Project Euler 9");
        expectedExercise.setDescription("description1");
        expectedExercise.setTest(newTest);
        expectedExercise.setCreator("creator1");
        expectedExercise.setSolutions(Map.of());

		when(exerciseRepository.findById(exercise.getId())).thenReturn(Optional.of(exercise));
		when(exerciseRepository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

		adminServiceImp.updateTestForExercise(exercise.getId(), newTest);

	    assertThat(exercise.getTest()).isEqualTo(newTest);

	    verify(exerciseRepository).findById(exercise.getId());
	    verify(exerciseRepository).save(exercise);
    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º"})
    void adminUpdateTestForExerciseTestExerciseNotFound(String exerciseId) {
    	String newTest = "newTest";

    	when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

    	assertThrows(ExerciceNotFoundException.class, () -> adminServiceImp.updateTestForExercise(exerciseId, newTest));
    }

    @Test
    void adminDeleteTestFromExerciseTest() {
    	Exercise exercise = new Exercise();
        exercise.setId("project-euler-9");
        exercise.setTitle("Project Euler 9");
        exercise.setDescription("description1");
        exercise.setTest("test");
        exercise.setCreator("creator1");
        exercise.setSolutions(Map.of());

		when(exerciseRepository.findById(exercise.getId())).thenReturn(Optional.of(exercise));

		adminServiceImp.deleteTestFromExercise(exercise.getId());

		verify(exerciseRepository).findById(exercise.getId());
		verify(exerciseRepository).save(exercise);
	    assertThat(exerciseRepository.findById(exercise.getId()).get().getTest()).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º"})
    void adminDeleteTestFromExerciseTestExerciseNotFound(String exerciseId) {
    	when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

    	assertThrows(ExerciceNotFoundException.class, () -> adminServiceImp.deleteTestFromExercise(exerciseId));
    }

    @Test
    void adminDeleteExerciseTest() {
    	Exercise exercise = new Exercise();
        exercise.setId("project-euler-9");
        exercise.setTitle("Project Euler 9");
        exercise.setDescription("description1");
        exercise.setTest("test");
        exercise.setCreator("creator1");
        exercise.setSolutions(Map.of());

        when(exerciseRepository.findById(exercise.getId())).thenReturn(Optional.of(exercise));

        adminServiceImp.deleteExercise(exercise.getId());

        verify(exerciseRepository).delete(exercise);
        assertThat(exerciseRepository.findById(exercise.getId()).equals(Optional.empty()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"43ero", "t3rreta", "+´`2marck", "º-º"})
    void adminDeleteExerciseTestExerciseNotFound(String exerciseId) {
    	when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

    	assertThrows(ExerciceNotFoundException.class, () -> adminServiceImp.deleteExercise(exerciseId));
    }
}

package es.tfg.codeguard.service.imp;

import java.util.Optional;

import es.tfg.codeguard.model.dto.UserPrivilegesDTO;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.util.CanNotModidyAdministratorException;
import es.tfg.codeguard.util.ExerciceNotFoundException;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.entity.deleteduser.DeletedUser;
import es.tfg.codeguard.model.entity.exercise.Exercise;
import es.tfg.codeguard.model.entity.user.User;
import es.tfg.codeguard.model.entity.userpass.UserPass;
import es.tfg.codeguard.model.repository.deleteduser.DeletedUserRepository;
import es.tfg.codeguard.model.repository.exercise.ExerciseRepository;
import es.tfg.codeguard.model.repository.user.UserRepository;
import es.tfg.codeguard.model.repository.userpass.UserPassRepository;
import es.tfg.codeguard.service.AdminService;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassRepository userPassRepository;
    @Autowired
    private DeletedUserRepository deletedUserRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private JWTService jwtService;

    @Override
    public UserDTO deleteUser(String username) {
        User user = userRepository.findById(username).orElseThrow(
                () -> new UserNotFoundException("User not found [ " + username + " ]"));

        UserPass userPass = userPassRepository.findById(username).orElseThrow(
                () -> new UserNotFoundException("User not found [ " + username + " ]"));

        checkAdmin(userPass);

        DeletedUser deletedUser = new DeletedUser(user);

        deletedUserRepository.save(deletedUser);

        userRepository.delete(user);

        return new UserDTO(deletedUser);
    }

    @Override
    public UserPassDTO updatePassword(String username, String newUserPass) {
        UserPass userPass = userPassRepository.findById(username).orElseThrow(
                () -> new UserNotFoundException("User not found [ " + username + " ]"));

        checkAdmin(userPass);

        try {
            checkPassword(newUserPass);
            userPass.setHashedPass(passwordEncoder.encode(newUserPass));
        } catch (PasswordNotValidException e) {
            throw new PasswordNotValidException("Password not valid [" + newUserPass + "]");
        }

        userPassRepository.save(userPass);

        return new UserPassDTO(userPass);

    }

    @Override
    public UserDTO updateUserPrivileges(UserPrivilegesDTO userPrivilegesDTO) {
        User user = userRepository.findById(userPrivilegesDTO.username()).orElseThrow(
                () -> new UserNotFoundException("User not found [ " + userPrivilegesDTO.username() + " ]"));

        UserPass userPass = userPassRepository.findById(userPrivilegesDTO.username()).orElseThrow(
                () -> new UserNotFoundException("User not found [ " + userPrivilegesDTO.username() + " ]")
        );

        checkAdmin(userPass);

        user.setTester(userPrivilegesDTO.tester());
        user.setCreator(userPrivilegesDTO.creator());

        userRepository.save(user);

        return new UserDTO(user);

    }
    
    @Override
    public ExerciseDTO updateTestForExercise(String userToken, String exerciseId, String test) {
		Exercise exercise = exerciseRepository.findById(exerciseId)
				.orElseThrow(() -> new ExerciceNotFoundException("Exercise not found [ " + exerciseId + " ]"));

        String username = jwtService.extractUserPass(userToken).getUsername();

		exercise.setTest(test);
        exercise.setTester(username);

		exerciseRepository.save(exercise);

		return new ExerciseDTO(exercise);
    }
    
    @Override
    public ExerciseDTO deleteTestFromExercise(String exerciseId) {
    	Exercise exercise = exerciseRepository.findById(exerciseId)
    			.orElseThrow(() -> new ExerciceNotFoundException("Exercise not found [ " + exerciseId + " ]"));

    	exercise.setTest(null);
        exercise.setTester(null);
    	
    	exerciseRepository.save(exercise);
    	
    	return new ExerciseDTO(exercise);
    }
    
	@Override
	public ExerciseDTO deleteExercise(String exerciseId) {
		Exercise exercise = exerciseRepository.findById(exerciseId)
				.orElseThrow(() -> new ExerciceNotFoundException("Exercise not found [ " + exerciseId + " ]"));

		exerciseRepository.delete(exercise);

		return new ExerciseDTO(exercise);
	}
	
	private void checkPassword(String password) {
		if (password == null || password.equals(""))
			throw new PasswordNotValidException("Password not valid [ " + password + " ]");
	}

    private void checkAdmin(UserPass userPass) {
        if (userPass.isAdmin()) {
            throw new CanNotModidyAdministratorException("Not allowed to modify administrator [ " + userPass.getUsername() + " ]");
        }
    }
}

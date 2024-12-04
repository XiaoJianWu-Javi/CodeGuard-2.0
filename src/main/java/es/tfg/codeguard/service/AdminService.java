package es.tfg.codeguard.service;

import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserPrivilegesDTO;

public interface AdminService {

	public abstract UserDTO deleteUser(String username);

	public abstract UserPassDTO updatePassword(String username, String newUserPass);

	public abstract UserDTO updateUserPrivileges(UserPrivilegesDTO userPrivilegesDTO);

	public abstract ExerciseDTO deleteTestFromExercise(String exerciseId);

	public abstract ExerciseDTO updateTestForExercise(String exerciseId, String test);

	public abstract ExerciseDTO deleteExercise(String exerciseId);

}

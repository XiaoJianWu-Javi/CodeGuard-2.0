package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.ExerciseDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserPrivilegesDTO;
import es.tfg.codeguard.util.ExerciseNotFoundException;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.tfg.codeguard.controller.AdminController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.AdminService;

@RestController
public class AdminControllerImp implements AdminController {

    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<UserDTO> deleteUser(@RequestParam String username) {

        try {
            return ResponseEntity.ok(adminService.deleteUser(username));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<UserPassDTO> updatePassword(AuthDTO authDTO) {

        try {
            return ResponseEntity.ok(adminService.updatePassword(authDTO.username(), authDTO.password()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PasswordNotValidException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @Override
    public ResponseEntity<UserDTO> updateUserPrivileges(UserPrivilegesDTO userPrivilegesDTO) {

        try{
            return ResponseEntity.ok(adminService.updateUserPrivileges(userPrivilegesDTO));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

	@Override
	public ResponseEntity<ExerciseDTO> updateTestForExercise(String exerciseId, String test) {
		try {
			return ResponseEntity.ok(adminService.updateTestForExercise(exerciseId, test));
		} catch (ExerciseNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<ExerciseDTO> deleteTestFromExercise(String exerciseId) {
		try {
            return ResponseEntity.ok(adminService.deleteTestFromExercise(exerciseId));
        } catch (ExerciseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
	}

	@Override
	public ResponseEntity<ExerciseDTO> deleteExercise(String exerciseId) {
		try {
			return ResponseEntity.ok(adminService.deleteExercise(exerciseId));
		} catch (ExerciseNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
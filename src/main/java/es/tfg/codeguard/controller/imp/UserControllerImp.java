package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import es.tfg.codeguard.controller.UserController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.UserService;

@RestController
public class UserControllerImp implements UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<UserDTO> deleteUser(@RequestHeader("Authorization") String userToken) {

        try {
            return ResponseEntity.ok(userService.deleteUser(userToken));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<UserDTO> getUserById(@PathVariable String username) {

        try{
            return ResponseEntity.ok(userService.getUserById(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {

        return ResponseEntity.ok(userService.getAllUsers());
    }


    @Override
    public ResponseEntity<UserDTO> changePassword(String userToken, ChangePasswordDTO changePasswordDTO) {

        try{
            return ResponseEntity.ok(userService.changePassword(userToken, changePasswordDTO));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (PasswordNotValidException i){
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }
}

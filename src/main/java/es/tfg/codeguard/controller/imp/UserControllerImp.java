package es.tfg.codeguard.controller.imp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import es.tfg.codeguard.controller.UserController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.UserService;

@RestController
public class UserControllerImp implements UserController {


    private final UserService userService;
    private final AdminService adminService;

    public UserControllerImp(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @Override
    public ResponseEntity<UserDTO> deleteUser(String userToken) {

        return userService.deleteUser(userToken)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<UserDTO> getUserById(@PathVariable String username) {

        return userService.getUserById(username)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserPassDTO> updateUser(@RequestParam String username, @RequestParam String newUserPass) {

        return adminService.updateUser(username, newUserPass)
                .map(userPassDTO -> new ResponseEntity<>(userPassDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));

        //TODO: Cambiar junto al serivcio cuando esté bien implementado el UPDATE
    }
}

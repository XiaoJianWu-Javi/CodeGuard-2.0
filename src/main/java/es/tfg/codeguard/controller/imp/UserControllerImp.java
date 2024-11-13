package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.UserController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class UserControllerImp implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName) {
        return userService.deleteUser(userName)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<UserPassDTO> deleteUser() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        //TODO: To implement

    }

    public ResponseEntity<UserDTO> getUserById(@PathVariable("userName") String userName) {
        return userService.getUserById(userName)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

    }

    public ResponseEntity<UserPassDTO> updateUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "newUserPass") String newUserPass) {

        return adminService.updateUser(userName, newUserPass)
                .map(userPassDTO -> new ResponseEntity<>(userPassDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));

        //TODO: Cambiar servicio cuando este bien implementado

    }

}

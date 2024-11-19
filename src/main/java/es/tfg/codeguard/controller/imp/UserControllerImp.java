package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.UserController;
import es.tfg.codeguard.model.dto.AuthenticationDTO;
import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserControllerImp implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName) {
        return userService.deleteUser(userName)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<UserDTO> deleteUser(Principal principal) {

        return userService.deleteUser(principal.getName())
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.BAD_REQUEST))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));


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

    @Override
    public ResponseEntity<UserPassDTO> authenticate(JsonParserUserPassDTO jsonParserUserPassDTO) {

        //TODO: IMPLEMENT USERSERVICE.AUTHENTICATE

        //return userService.authenticate(authenticationDTO, bindingResult)
        return ResponseEntity.badRequest().build();
    }

}

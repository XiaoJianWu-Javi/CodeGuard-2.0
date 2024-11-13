package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.RegisterController;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterControllerImp implements RegisterController {

    @Autowired
    private UserService userService;

    public ResponseEntity<UserPassDTO> registerUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "userPassword") String userPassword) {

        return userService.registerUser(userName, userPassword)
                .map(userPassDTO -> new ResponseEntity<>(userPassDTO, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        //TODO: Implementar el Status code 409 (CONFLICT)

    }

}

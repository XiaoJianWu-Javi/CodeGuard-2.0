package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.LoginController;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControllerImp implements LoginController {

    @Autowired
    private UserService userService;

    public ResponseEntity<UserPassDTO> loginUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "userPassword") String userPassword) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

//        return userService.loginUser(userName, userPassword)
//                .map(userPassDto -> new ResponseEntity<>(userPassDto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    public ResponseEntity<UserPassDTO> logoutUser(@RequestParam(name = "userName") String userName) {

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

//        return userService.logoutUser(userName)
//                .map(userPassDto -> new ResponseEntity<>(userPassDto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}

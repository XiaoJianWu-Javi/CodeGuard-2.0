package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.LoginController;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginControllerImp implements LoginController {
    //TODO: Use interface
    //TODO: Use interface

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping(name = "/login", consumes = "application/json")
    public ResponseEntity login(@RequestBody AuthRequest user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    class AuthRequest {
        private String username;
        private String password;

        
        public void setUsername(String username) {
            this.username = username;
        }

        
        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        
        public void setPassword(String password) {
            this.password = password;
        }
        
    }

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

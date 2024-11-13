package es.tfg.codeguard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class LoginController {

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
}
 
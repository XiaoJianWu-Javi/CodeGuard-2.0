package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.LoginController;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.JWTService;
import es.tfg.codeguard.service.LoginService;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginControllerImp implements LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTService jwtService;

    @Override
    public ResponseEntity<UserPassDTO> loginUser(@RequestBody AuthDTO authDTO) {

        UserPassDTO userPassDTO = null;

        try {
            userPassDTO = loginService.loginUser(authDTO.username(), authDTO.password());
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtService.createJwt(userPassDTO));

        return ResponseEntity.ok().headers(headers).body(userPassDTO);

    }
}
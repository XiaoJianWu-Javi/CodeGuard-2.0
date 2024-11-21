package es.tfg.codeguard.controller.imp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.tfg.codeguard.util.UsernameNotValid;
import es.tfg.codeguard.controller.RegisterController;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.RegisterService;

@RestController
public class RegisterControllerImp implements RegisterController {

    private final RegisterService registerService;

    public RegisterControllerImp(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public ResponseEntity<UserPassDTO> registerUser(@RequestBody AuthDTO authDTO) {

        try{
            return registerService.registerUser(authDTO)
                    .map(userPass -> new ResponseEntity<>(userPass, HttpStatus.CREATED))
                    .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
        } catch (UsernameNotValid e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

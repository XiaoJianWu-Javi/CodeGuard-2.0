package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UsernameAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.tfg.codeguard.controller.RegisterController;
import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.RegisterService;
import es.tfg.codeguard.util.UsernameNotValidException;

@RestController
public class RegisterControllerImp implements RegisterController {

    @Autowired
    private RegisterService registerService;

    @Override
    public ResponseEntity<UserPassDTO> registerUser(@RequestBody AuthDTO authDTO) {

        try {
            return new ResponseEntity<>(registerService.registerUser(authDTO), HttpStatus.CREATED);
        } catch (UsernameNotValidException | PasswordNotValidException e) {
            return ResponseEntity.badRequest().build();
        } catch (UsernameAlreadyExistException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}

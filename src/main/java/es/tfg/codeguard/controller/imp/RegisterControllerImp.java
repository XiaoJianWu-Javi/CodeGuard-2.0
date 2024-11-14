package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.RegisterController;
import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.RegisterService;

import es.tfg.codeguard.util.UserNameNotValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterControllerImp implements RegisterController {

    @Autowired
    private RegisterService registerService;

    public ResponseEntity<UserPassDTO> registerUser(@RequestBody JsonParserUserPassDTO jsonParserUserPassDTO) {

        try{
            return registerService.registerUser(jsonParserUserPassDTO)
                    .map(userPass -> new ResponseEntity<>(userPass, HttpStatus.CREATED))
                    .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
        }catch (UserNameNotValid e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

}

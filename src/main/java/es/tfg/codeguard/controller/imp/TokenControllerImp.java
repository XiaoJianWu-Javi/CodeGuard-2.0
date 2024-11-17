package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.TokenController;
import es.tfg.codeguard.model.dto.*;
import es.tfg.codeguard.service.UserService;
import es.tfg.codeguard.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/token")
public class TokenControllerImp implements TokenController {

    //@Autowired
    //private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImp userServiceImp;

    @Autowired
    private JWTUtilToken jwtUtilToken;

    @Override
    public ApiResponse<AuthToken> generateToken(JsonParserUserPassDTO jsonParserUserPassDTO) {

        //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jsonParserUserPassDTO.getUsername(), jsonParserUserPassDTO.getPassword()));
        final Optional<UserDTO> userDTO = userServiceImp.getUserById(jsonParserUserPassDTO.getUsername());

        final String token = jwtUtilToken.generateToken(jsonParserUserPassDTO.getUsername());

        return new ApiResponse<>(200,"ESTA TODO CORRECTO", new AuthToken(jsonParserUserPassDTO.getUsername(), token));


    }
}

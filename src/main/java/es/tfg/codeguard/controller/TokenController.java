package es.tfg.codeguard.controller;


import es.tfg.codeguard.model.dto.ApiResponse;
import es.tfg.codeguard.model.dto.AuthToken;
import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/token")
public interface TokenController {

    public ApiResponse<AuthToken> generateToken(@RequestBody JsonParserUserPassDTO jsonParserUserPassDTO);

}

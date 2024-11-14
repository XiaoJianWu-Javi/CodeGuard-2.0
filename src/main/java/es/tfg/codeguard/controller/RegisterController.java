package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public interface RegisterController {

    @PostMapping("")
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User register succesfully"),
            @ApiResponse(responseCode = "400", description = "User name not valid"),
            @ApiResponse(responseCode = "409", description = "User couldn`t be register")
    })
    public ResponseEntity<UserPassDTO> registerUser(@RequestBody JsonParserUserPassDTO user);
}

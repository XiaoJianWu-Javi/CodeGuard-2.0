package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public interface RegisterController {

    @PostMapping("")
    @ApiOperation("Register new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User register succesfully"),
            @ApiResponse(code = 409, message = "User couldn`t be register"),
            @ApiResponse(code = 400, message = "User name not valid")
    })
    public ResponseEntity<UserPassDTO> registerUser(String userName, String userPassword);
}

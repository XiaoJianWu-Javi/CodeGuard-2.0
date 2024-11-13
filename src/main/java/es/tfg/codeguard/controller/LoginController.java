package es.tfg.codeguard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/login")
public interface LoginController {

    @PostMapping(name = "", consumes = "application/json")
    @ApiOperation("Login user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User logged succesfully"),
            @ApiResponse(code = 400, message = "User couldn`t login")
    })
    public ResponseEntity<UserPassDTO> loginUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "userPassword") String userPassword);


    @GetMapping("/logout")
    @ApiOperation("Logout user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User logout succesfully"),
            @ApiResponse(code = 400, message = "User couldn't logout")
    })
    public ResponseEntity<UserPassDTO> logoutUser(@RequestParam(name = "userName") String userName);


}

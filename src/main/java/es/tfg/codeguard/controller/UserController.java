package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wizard")
public interface UserController {

    @GetMapping("/register")
    @ApiOperation("Register new Wizard")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Wizard register succesfully"),
            @ApiResponse(code = 404, message = "Wizard couldn`t register")
    })
    public ResponseEntity<UserPassDTO> registerUser(String userName, String userPassword);


    @GetMapping("/delete")
    @ApiOperation("Delete Wizard")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Wizard couldn't be deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "UserName") String UserName);

    @GetMapping("/{wizardId}")
    @ApiOperation("Get Wizard by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Wizard found"),
            @ApiResponse(code = 404, message = "Any Wizard found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userName") String userName);


    @GetMapping("/allUsers")
    @ApiOperation("Get all Users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users found")
    })
    public ResponseEntity<List<UserDTO>> getAllUser();

}

package es.tfg.codeguard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public interface UserController {

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User couldn't be found")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestHeader("Authorization") String userToken);

    @GetMapping("/{username}")
    @Operation(summary = "Get user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable String username);

    @GetMapping("/allUsers")
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found")
    })
    public ResponseEntity<List<UserDTO>> getAllUser();

    @PatchMapping("/updateUser")
    @Operation(summary = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated sucsessfully"),
            @ApiResponse(responseCode = "304", description = "Password couldn't de updated")
    })
    public ResponseEntity<UserPassDTO> updateUser(@RequestParam String username, @RequestParam String newUserPass);


}

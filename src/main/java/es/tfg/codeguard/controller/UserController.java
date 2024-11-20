package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public interface UserController {

    //TODO: Eliminar este método

    @DeleteMapping("/deleteUser")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User couldn't be deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "UserName") String UserName);

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "User couldn't be deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(Principal principal);

    @GetMapping("/{userName}")
    @Operation(summary = "Get user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "Any user found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userName") String userName);

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
            @ApiResponse(responseCode = "304", description = "Password couldn't updated")
    })
    public ResponseEntity<UserPassDTO> updateUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "newUserPass") String newUserPass);

}

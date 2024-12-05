package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.ChangePasswordDTO;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserRestoreDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/restoreUser")
    @Operation(summary = "Restore user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "User couldn't be found"),
            @ApiResponse(responseCode = "400", description = "Incorrect password")
    })
    public ResponseEntity<UserDTO> restoreUser(@RequestBody UserRestoreDTO restoreDTO);
    
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

    @PatchMapping("/changePassword")
    @Operation(summary = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated sucsessfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "304", description = "The new password is not valid"),
            @ApiResponse(responseCode = "400", description = "Old password incorrect")
    })
    public ResponseEntity<UserDTO> changePassword(@RequestHeader("Authorization") String userToken, @RequestBody ChangePasswordDTO changePasswordDTO);


}

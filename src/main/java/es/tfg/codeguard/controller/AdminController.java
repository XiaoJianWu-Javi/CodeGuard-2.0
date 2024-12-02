package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.model.dto.UserPrivilegesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.tfg.codeguard.model.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public interface AdminController {

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User couldn't be found")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam String username);


    @PatchMapping("/updateUserPassword")
    @Operation(summary = "Change password to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Password not valid")
    })
    public ResponseEntity<UserPassDTO> updatePassword(@RequestBody AuthDTO authDTO);


    @PatchMapping("/updateUserPrivileges")
    @Operation(summary = "Change privileges to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User privileges changed successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> updateUserPrivileges(@RequestBody UserPrivilegesDTO userPrivilegesDTO);

}
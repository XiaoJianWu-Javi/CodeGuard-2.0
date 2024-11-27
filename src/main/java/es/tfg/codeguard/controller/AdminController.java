package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
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


    @PatchMapping("/updatePassword")
    @Operation(summary = "Change password to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Password not valid")
    })
    public ResponseEntity<UserPassDTO> updateUser(@RequestBody AuthDTO authDTO);


    @PatchMapping("/grantTester")
    @Operation(summary = "Grant tester role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grant tester role successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Could not grant tester role to user")
    })
    public ResponseEntity<UserDTO> grantTester(String username);


    @PatchMapping("/grantCreator")
    @Operation(summary = "Grant creator role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grant creator role successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Could not grant creator role to user")
    })
    public ResponseEntity<UserDTO> grantCreator(String username);


    @PatchMapping("/revokeTester")
    @Operation(summary = "Revoke tester role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revoke tester role successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Could not revoke tester role to user")
    })
    public ResponseEntity<UserDTO> revokeTester(String username);


    @PatchMapping("/revokeCreator")
    @Operation(summary = "Revoke creator role to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revoke creator role successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Could not revoke creator role to user")
    })
    public ResponseEntity<UserDTO> revokeCreator(String username);


    @PatchMapping("/grantAllPrivileges")
    @Operation(summary = "Grant all privileges to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grant all privileges successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Could not grant all privileges to user")
    })
    public ResponseEntity<UserDTO> grantAllPrivileges(String username);


    @PatchMapping
    @Operation(summary = "Revoke al privileges to user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Revoke all privileges successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Could not revoke all privileges to user")
    })
    public ResponseEntity<UserDTO> revokeAllPrivileges(String username);

}
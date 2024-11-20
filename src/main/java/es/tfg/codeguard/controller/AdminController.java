package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public interface AdminController {

    @DeleteMapping("/delete")
    @Operation(summary = "Delete user by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted succsesfully"),
            @ApiResponse(responseCode = "404", description = "User couldn't deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName);




}

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
@RequestMapping("/user")
public interface UserController {

    //TODO: Eliminar este método

    @DeleteMapping("/deleteUser")
    @ApiOperation("Delete user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "User couldn't be deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "UserName") String UserName);

    @DeleteMapping("/delete")
    @ApiOperation("Delete user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "User couldn't be deleted")
    })
    public ResponseEntity<UserPassDTO> deleteUser();

    @GetMapping("/{userName}")
    @ApiOperation("Get user by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found"),
            @ApiResponse(code = 404, message = "Any user found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userName") String userName);

    @GetMapping("/allUsers")
    @ApiOperation("Get all users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Users found")
    })
    public ResponseEntity<List<UserDTO>> getAllUser();

    @PatchMapping("/updateUser")
    @ApiOperation("Update user password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Password updated sucsessfully"),
            @ApiResponse(code = 304, message = "Password couldn't updated")
    })
    public ResponseEntity<UserPassDTO> updateUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "newUserPass") String newUserPass);

}

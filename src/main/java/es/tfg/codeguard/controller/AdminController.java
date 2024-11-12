package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public interface AdminController {

    @DeleteMapping("/delete")
    @ApiOperation("Delete user by name")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User deleted succsesfully"),
            @ApiResponse(code = 404, message = "User couldn't deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName);


    @PatchMapping("/updateUser")
    @ApiOperation("Update user password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Password updated sucsessfully"),
            @ApiResponse(code = 403, message = "Password couldn't updated")
    })
    public ResponseEntity<UserPassDTO> updateUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "newUserPass") String newUserPass);


}

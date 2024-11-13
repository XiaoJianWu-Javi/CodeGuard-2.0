package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserDTO;
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




}

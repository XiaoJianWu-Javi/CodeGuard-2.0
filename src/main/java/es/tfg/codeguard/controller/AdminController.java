package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elder")
public interface AdminController {

    @GetMapping("/delete")
    @ApiOperation("Delete Wizard by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Wizard deleted succsesfully"),
            @ApiResponse(code = 404, message = "Wizard couldn't deleted")
    })
    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName);


    @GetMapping("/updateUser")
    @ApiOperation("Update User password")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Password updated sucsessfully"),
            @ApiResponse(code = 403, message = "Password couldn't updated")
    })
    public ResponseEntity<UserPassDTO> updateUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "newUserPass") String newUserPass);


}

package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.AdminController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminControllerImp implements AdminController {

    @Autowired
    private AdminService adminService;


    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName) {

        return adminService.deleteUser(userName)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
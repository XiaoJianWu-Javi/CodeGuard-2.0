package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.tfg.codeguard.controller.AdminController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.AdminService;

@RestController
public class AdminControllerImp implements AdminController {

    @Autowired
    private AdminService adminService;

    @Override
    public ResponseEntity<UserDTO> deleteUser(@RequestParam String username) {

        try{
            return ResponseEntity.ok(adminService.deleteUser(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }

    }
}

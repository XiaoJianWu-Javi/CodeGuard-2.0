package es.tfg.codeguard.controller.imp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.tfg.codeguard.controller.AdminController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.service.AdminService;

@RestController
public class AdminControllerImp implements AdminController {

    private final AdminService adminService;

    public AdminControllerImp(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public ResponseEntity<UserDTO> deleteUser(@RequestParam String username) {

        return adminService.deleteUser(username)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}

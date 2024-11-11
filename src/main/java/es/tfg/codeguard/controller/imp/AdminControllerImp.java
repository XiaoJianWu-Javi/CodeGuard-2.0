package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.AdminController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class AdminControllerImp implements AdminController {

    @Autowired
    private AdminService adminService;


    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName) {

        return adminService.deleteUser(userName)
                .map(wizardDTO -> new ResponseEntity<>(wizardDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    public ResponseEntity<UserPassDTO> updateUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "newUserPass") String newUserPass) {

        return adminService.updateUser(userName, newUserPass)
                .map(userPassDTO -> new ResponseEntity<>(userPassDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_MODIFIED));

    }

}

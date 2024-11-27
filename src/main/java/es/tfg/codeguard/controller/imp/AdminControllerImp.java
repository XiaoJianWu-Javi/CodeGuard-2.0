package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.model.dto.AuthDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.util.PasswordNotValidException;
import es.tfg.codeguard.util.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ResponseEntity<UserPassDTO> updateUser(AuthDTO authDTO) {

        try{
            return ResponseEntity.ok(adminService.updateUser(authDTO.username(), authDTO.password()));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (PasswordNotValidException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @Override
    public ResponseEntity<UserDTO> grantTester(String username) {
        try{
            return  ResponseEntity.ok(adminService.grantTester(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> grantCreator(String username) {
        try{
            return  ResponseEntity.ok(adminService.grantCreator(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> revokeTester(String username) {
        try{
            return  ResponseEntity.ok(adminService.revokeTester(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> revokeCreator(String username) {
        try{
            return  ResponseEntity.ok(adminService.revokeCreator(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> grantAllPrivileges(String username) {
        try{
            return  ResponseEntity.ok(adminService.grantAllPrivileges(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<UserDTO> revokeAllPrivileges(String username) {
        try{
            return  ResponseEntity.ok(adminService.revokeAllPrivileges(username));
        }catch (UserNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

}
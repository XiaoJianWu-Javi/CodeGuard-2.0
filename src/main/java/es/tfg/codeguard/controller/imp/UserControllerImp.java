package es.tfg.codeguard.controller.imp;

import es.tfg.codeguard.controller.UserController;
import es.tfg.codeguard.model.dto.UserDTO;
import es.tfg.codeguard.model.dto.UserPassDTO;
import es.tfg.codeguard.service.AdminService;
import es.tfg.codeguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class UserControllerImp implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;


    public ResponseEntity<UserPassDTO> registerUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "userPassword") String userPassword) {

        return userService.registerUser(userName, userPassword)
                .map(wizardDto -> new ResponseEntity<>(wizardDto, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName) {
        return userService.deleteUser(userName)
                .map(wizardDto -> new ResponseEntity<>(wizardDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    public ResponseEntity<UserDTO> getUserById(@PathVariable("userName") String userName) {
        return userService.getUserById(userName)
                .map(wizardDto -> new ResponseEntity<>(wizardDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

    }


//TODO: Incremento de código Strint 2

//    @GetMapping("/login")
//    @ApiOperation("Login Wizard")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Wizard logged succesfully"),
//            @ApiResponse(code = 404, message = "Wizard couldn`t login")
//    })
//    public ResponseEntity<UserPassDTO> loginWizard(@RequestParam(name = "wizardName") String wizardName, @RequestParam(name = "wizardPassword") String wizardPassword) {
//        return userService.loginUser(wizardName, wizardPassword)
//                .map(wizardDto -> new ResponseEntity<>(wizardDto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

//    @GetMapping("/logout")
//    @ApiOperation("Logout Wizard")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Wizard logout succesfully"),
//            @ApiResponse(code = 404, message = "Wizard couldn't logout")
//    })
//    public ResponseEntity<UserPassDTO> logoutWizard(@RequestParam(name = "wizardName") String wizardName) {
//        return userService.logoutUser(wizardName)
//                .map(wizardDto -> new ResponseEntity<>(wizardDto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

}

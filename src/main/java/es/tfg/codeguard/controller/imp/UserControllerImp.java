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
                .map(userPassDTO -> new ResponseEntity<>(userPassDTO, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<UserDTO> deleteUser(@RequestParam(name = "userName") String userName) {
        return userService.deleteUser(userName)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }


    public ResponseEntity<UserDTO> getUserById(@PathVariable("userName") String userName) {
        return userService.getUserById(userName)
                .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUser() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

    }


//TODO: Incremento de código Strint 2

//    @GetMapping("/login")
//    @ApiOperation("Login user")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "User logged succesfully"),
//            @ApiResponse(code = 404, message = "User couldn`t login")
//    })
//    public ResponseEntity<UserPassDTO> loginUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "userPassword") String userPassword) {
//        return userService.loginUser(userName, userPassword)
//                .map(userPassDto -> new ResponseEntity<>(userPassDto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

//    @GetMapping("/logout")
//    @ApiOperation("Logout user")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "User logout succesfully"),
//            @ApiResponse(code = 404, message = "User couldn't logout")
//    })
//    public ResponseEntity<UserPassDTO> logoutUser(@RequestParam(name = "userName") String userName) {
//        return userService.logoutUser(userName)
//                .map(userPassDto -> new ResponseEntity<>(userPassDto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

}

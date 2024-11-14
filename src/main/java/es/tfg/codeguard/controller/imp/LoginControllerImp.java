// package es.tfg.codeguard.controller.imp;

// import es.tfg.codeguard.controller.LoginController;
// import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
// import es.tfg.codeguard.model.dto.UserPassDTO;
// import es.tfg.codeguard.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class LoginControllerImp implements LoginController {

//     @Autowired
//     private UserService userService;
//     @Autowired
//     private AuthenticationManager authenticationManager;
    
//     @Override
//     public ResponseEntity loginUser(@RequestBody JsonParserUserPassDTO user) {
//         try {
//             Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//             );
//             return new ResponseEntity<>(HttpStatus.OK);
//         } catch(AuthenticationException e) {
//             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//         }

//         //return userService.loginUser(userName, userPassword)
//         //                .map(userPassDto -> new ResponseEntity<>(userPassDto, HttpStatus.OK))
//         //                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//     }

//     @Override
//     public ResponseEntity<UserPassDTO> logoutUser() {

//         return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

// //        return userService.logoutUser(userName)
// //                .map(userPassDto -> new ResponseEntity<>(userPassDto, HttpStatus.OK))
// //                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//     }

// }

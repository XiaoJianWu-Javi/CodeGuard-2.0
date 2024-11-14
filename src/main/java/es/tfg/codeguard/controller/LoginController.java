// package es.tfg.codeguard.controller;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import es.tfg.codeguard.model.dto.JsonParserUserPassDTO;
// import es.tfg.codeguard.model.dto.UserPassDTO;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.responses.ApiResponses;

// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/login")
// public interface LoginController {

//     @PostMapping(name = "", consumes = "application/json")
//     @Operation(summary = "Login user")
//     @ApiResponses(value = {
//             @ApiResponse(responseCode = "200", description = "User logged succesfully"),
//             @ApiResponse(responseCode = "400", description = "User couldn`t be login")
//     })
//     public ResponseEntity<UserPassDTO> loginUser(@RequestBody JsonParserUserPassDTO user);


//     @GetMapping("/logout")
//     @Operation(summary = "Logout user")
//     @ApiResponses( value = {
//             @ApiResponse(responseCode = "200", description = "User logout succesfully"),
//             @ApiResponse(responseCode = "400", description = "User couldn't be logout")
//     })
//     public ResponseEntity<UserPassDTO> logoutUser();

// }

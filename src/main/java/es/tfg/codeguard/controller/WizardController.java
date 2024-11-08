package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.dto.PasswordWizardDTO;
import es.tfg.codeguard.model.dto.WizardDTO;
import es.tfg.codeguard.model.entity.DeadWizard;
import es.tfg.codeguard.service.WizardService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/wizard")
public class WizardController {

    @Autowired
    private WizardService wizardService;

    @GetMapping("/register")
    @ApiOperation("Register new Wizard")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Wizard register succesfully"),
            @ApiResponse(code = 404, message = "Wizard couldn`t register")
    })
    public ResponseEntity<Optional<PasswordWizardDTO>> registerWizard(@RequestParam(name = "wizardName") String wizardName, @RequestParam(name = "wizardPassword") String wizardPassword) {

        return wizardService.registerWizard(wizardName, wizardPassword)
                .map(wizardDto -> new ResponseEntity<>(Optional.ofNullable(wizardDto), HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND));
    }

//    @GetMapping("/login")
//    @ApiOperation("Login Wizard")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Wizard logged succesfully"),
//            @ApiResponse(code = 404, message = "Wizard couldn`t login")
//    })
//    public ResponseEntity<Optional<WizardDTO>> loginWizard(@RequestParam(name = "wizardName") String wizardName, @RequestParam(name = "wizardPassword") String wizardPassword) {
//        return wizardService.loginWizard(wizardName, wizardPassword)
//                .map(wizardDto -> new ResponseEntity<>(Optional.ofNullable(wizardDto), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND));
//    }

//    @GetMapping("/logout")
//    @ApiOperation("Logout Wizard")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Wizard logout succesfully"),
//            @ApiResponse(code = 404, message = "Wizard couldn't logout")
//    })
//    public ResponseEntity<Optional<WizardDTO>> logoutWizard(@RequestParam(name = "wizardName") String wizardName) {
//        return wizardService.logoutWizard(wizardName)
//                .map(wizardDto -> new ResponseEntity<>(Optional.ofNullable(wizardDto), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND));
//    }

    @GetMapping("/delete")
    @ApiOperation("Delete Wizard")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Wizard couldn't be deleted")
    })
    public ResponseEntity<Optional<DeadWizard>> deleteWizard(@RequestParam(name = "wizardName") String wizardName) {
        return wizardService.deleteWizard(wizardName)
                .map(wizardDto -> new ResponseEntity<>(Optional.ofNullable(wizardDto), HttpStatus.OK))
                .orElse(new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND));



    }

//    @GetMapping("/{wizardId}")
//    @ApiOperation("Get Wizard by name")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Wizard found"),
//            @ApiResponse(code = 404, message = "Any Wizard found")
//    })
//    public ResponseEntity<Optional<WizardDTO>> getWizardById(@PathVariable("wizardId") String wizardName) {
//        return wizardService.getWizardById(wizardName)
//                .map(wizardDto -> new ResponseEntity<>(Optional.ofNullable(wizardDto), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND));
//    }

//    @GetMapping("/updateWizardsPassword")
//    @ApiOperation("Update Wizard password")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "Password updated sucsessfully"),
//            @ApiResponse(code = 403, message = "Password couldn't updated")
//    })
//    public ResponseEntity<Optional<WizardDTO>> updateWizard(@RequestParam(name = "wizardName") String wizardName, @RequestParam(name = "newWizardPassword") String newWizardPassword) {
//
//        return wizardService.updateWizard(wizardName, newWizardPassword)
//                .map(wizardDTO -> new ResponseEntity<>(Optional.ofNullable(wizardDTO), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_MODIFIED));
//
//    }

}

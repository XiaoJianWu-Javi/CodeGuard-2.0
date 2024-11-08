package es.tfg.codeguard.controller;

import es.tfg.codeguard.model.WizardDTO;
import es.tfg.codeguard.service.ElderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/elder")
public class ElderController {

    @Autowired
    private ElderService elderService;

    @GetMapping("/delete")
    @ApiOperation("Delete Wizard by Id")
    @ApiResponses({
            @ApiResponse(code=200,message="Wizard deleted succsesfully"),
            @ApiResponse(code=404,message="Wizard couldn't deleted")
    })
    public ResponseEntity<Optional<WizardDTO>> deleteWizardById(@RequestParam(name ="wizardName") String wizardId){

        return elderService.deleteWizardById(wizardId).map(wizardDTO -> new ResponseEntity<>(Optional.ofNullable(wizardDTO), HttpStatus.OK)).orElse(new ResponseEntity<>(Optional.empty(),HttpStatus.NOT_FOUND));

    }

    @GetMapping("/allWizards")
    @ApiOperation("Get all Wizards")
    @ApiResponses({
            @ApiResponse(code=200,message = "OK"),
            @ApiResponse(code=404,message = "Could not find any Wizard")
    })
    public ResponseEntity<List<WizardDTO>> getAllWizards(){

        return new ResponseEntity<>(elderService.getAllWizards(), HttpStatus.OK);

    }

}

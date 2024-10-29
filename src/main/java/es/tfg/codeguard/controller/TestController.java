package es.tfg.codeguard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO: BORRAR ESTE ARCHIVO

@RestController
@RequestMapping("/testcontroller")
public interface TestController {

    @GetMapping("/holamundo")    
    public String getHolaMundo();
}

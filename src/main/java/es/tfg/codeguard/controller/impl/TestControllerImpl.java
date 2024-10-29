package es.tfg.codeguard.controller.impl;

import org.springframework.web.bind.annotation.RestController;

import es.tfg.codeguard.controller.TestController;

//TODO: BORRAR ESTE ARCHIVO

@RestController
public class TestControllerImpl implements TestController {

    @Override
    public String getHolaMundo() {
        return "HolaMundo";
    }
    
}

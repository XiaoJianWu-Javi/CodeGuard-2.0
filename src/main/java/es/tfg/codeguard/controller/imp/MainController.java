package es.tfg.codeguard.controller.imp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    @GetMapping("/home")
    public String handleHome(@RequestParam String param) {
        return "home";
    }

    @GetMapping("/user/home")
    public String handleUserHome(@RequestParam String param) {
        return "home-user";
    }

    @GetMapping("/admin/home")
    public String handleAdminHome(@RequestParam String param) {
        return "home-admin";
    }
    
}

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//Indica que esta clase es un controlador de Spring MVC
@Controller 
public class HomeController {

	// Mapea la ruta raíz del proyecto (http://localhost:8082/)
    @GetMapping("/") 
    public String home() {
        // Redirige automáticamente a la lista de clientes
        return "redirect:/clientes";
    }
}

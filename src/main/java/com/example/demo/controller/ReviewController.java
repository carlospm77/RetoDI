package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Review;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ReviewService;

//Ruta base para todas las operaciones relacionadas con reviews
@Controller
@RequestMapping("/reviews") 
public class ReviewController {
	
	// Servicio que gestiona la lógica de negocio de las reviews
    @Autowired
    private ReviewService reviewService;

    // Servicio para obtener información de clientes
    @Autowired
    private ClienteService clienteService; 

    // LISTAR TODAS LAS REVIEWS
    @GetMapping
    public String listarReviews(Model model) {
        // Obtiene todas las reviews y las envía a la vista
        model.addAttribute("reviews", reviewService.mostrarTodas());
        // Carga la plantilla lista.html
        return "reviews/lista"; 
    }

    // FORMULARIO PARA CREAR UNA NUEVA REVIEW
    @GetMapping("/nuevo")
    public String nuevoFormReview(Model model) {
        // Se envía un objeto Review vacío al formulario
        model.addAttribute("review", new Review());

        // También se envía la lista de clientes para seleccionar uno
        model.addAttribute("clientes", clienteService.mostrarTodos());

        // Carga la plantilla form.html
        return "reviews/form"; 
    }

    // GUARDAR REVIEW (CREAR O EDITAR)   
    @PostMapping
    public String guardarReview(@ModelAttribute Review review) {

        if (review.getId() != null) {
            // Es edición → mantener el cliente original
            Review original = reviewService.buscarPorId(review.getId());
            review.setCliente(original.getCliente());
        } else {
            // Es nueva → cargar cliente desde BD
            Cliente cliente = clienteService.buscarPorId(review.getCliente().getId());
            review.setCliente(cliente);
        }
        // Redirige a la lista de reviews
        reviewService.guardar(review);
        return "redirect:/reviews";
    }


    // FORMULARIO PARA EDITAR UNA REVIEW EXISTENTE
    @GetMapping("/editar/{id}")
    public String editarFormReview(@PathVariable Long id, Model model) {

        // Se busca la review por ID y se envía al formulario
        model.addAttribute("review", reviewService.buscarPorId(id));

        return "reviews/form";
    }

    // ELIMINAR UNA REVIEW
    @GetMapping("/eliminar/{id}")
    public String eliminarReview(@PathVariable Long id) {

        // Elimina la review por su ID
        reviewService.eliminar(id);

        // Redirige a la lista
        return "redirect:/reviews"; 
    }
}

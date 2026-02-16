package com.example.demo.controller;

// Importaciones necesarias para Spring MVC y el modelo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

//Ruta base para todas las operaciones de clientes
@Controller
@RequestMapping("/clientes") 
public class ClienteController {

	// Inyección del servicio que maneja la lógica de negocio de clientes
    @Autowired
    private ClienteService clienteService;     

    // LISTAR CLIENTES
    @GetMapping
    public String listarClientes(Model model) {
        // Obtiene todos los clientes desde el servicio
        model.addAttribute("clientes", clienteService.mostrarTodos());
        
        // Devuelve la vista "clientes/lista"
        return "clientes/lista";
    }
    
    // FORMULARIO NUEVO CLIENTE
    @GetMapping("/nuevo")
    public String nuevoFormCliente(Model model) {
        // Se envía un objeto Cliente vacío al formulario
        model.addAttribute("cliente", new Cliente());
        
        // Carga la vista del formulario
        return "clientes/form";
    }
    
    // GUARDAR CLIENTE (CREAR O EDITAR)
    @PostMapping
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        // Guarda o actualiza el cliente según si tiene ID o no
        clienteService.guardar(cliente);
        
        // Redirige a la lista de clientes
        return "redirect:/clientes";
    }

    // FORMULARIO EDITAR CLIENTE
    @GetMapping("/editar/{id}")
    public String editarFormCliente(@PathVariable Long id, Model model) {
        // Busca el cliente por ID y lo envía al formulario
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        
        return "clientes/form";
    }

    // ELIMINAR CLIENTE
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        // Elimina el cliente por ID
        clienteService.eliminar(id);
        
        // Redirige a la lista
        return "redirect:/clientes";
    }
}

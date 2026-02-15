package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Cliente;
import com.example.demo.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
//@CrossOrigin(origins = "*") // opcional, útil si consumes desde Angular u otro frontend
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    // LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.mostrarTodos());
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    // CREAR CLIENTE
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.guardar(cliente);
        return ResponseEntity.ok(nuevo);
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente existente = clienteService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualiza campos
        existente.setNombre(cliente.getNombre());
        existente.setEdad(cliente.getEdad());
        existente.setGenero(cliente.getGenero());
        existente.setIntolerancia(cliente.isIntolerancia());
        existente.setDetalleIntolerancia(cliente.getDetalleIntolerancia());

        Cliente actualizado = clienteService.guardar(existente);
        return ResponseEntity.ok(actualizado);
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        Cliente existente = clienteService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

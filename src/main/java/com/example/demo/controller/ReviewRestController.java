package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Review;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ReviewService;

@RestController
@RequestMapping("/api/reviews")
//@CrossOrigin(origins = "*") // opcional, útil si consumes desde Angular u otro frontend
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ClienteService clienteService;

    // LISTAR TODAS LAS REVIEWS
    @GetMapping
    public ResponseEntity<List<Review>> listarReviews() {
        return ResponseEntity.ok(reviewService.mostrarTodas());
    }

    // OBTENER REVIEW POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> obtenerReview(@PathVariable Long id) {
        Review review = reviewService.buscarPorId(id);
        if (review == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(review);
    }

    // CREAR REVIEW
    @PostMapping
    public ResponseEntity<Review> crearReview(@RequestBody Review review) {

        // Validar cliente
        if (review.getCliente() == null || review.getCliente().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Cliente cliente = clienteService.buscarPorId(review.getCliente().getId());
        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }

        review.setCliente(cliente);
        Review nueva = reviewService.guardar(review);

        return ResponseEntity.ok(nueva);
    }

    // ACTUALIZAR REVIEW
    @PutMapping("/{id}")
    public ResponseEntity<Review> actualizarReview(@PathVariable Long id, @RequestBody Review review) {

        Review existente = reviewService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        // Mantener el cliente original
        review.setCliente(existente.getCliente());

        // Campos a editar
        existente.setDescripcion(review.getDescripcion());
        existente.setValoracion(review.getValoracion());

        Review actualizada = reviewService.guardar(review);
        return ResponseEntity.ok(actualizada);
    }

    // ELIMINAR REVIEW
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReview(@PathVariable Long id) {
        Review existente = reviewService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        reviewService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

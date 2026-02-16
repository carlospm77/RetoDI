package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Review;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ReviewService;
import org.springframework.http.ResponseEntity;


@SpringBootTest(properties = "spring.profiles.active=test")
class ReviewRestControllerTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ReviewRestController controller;

    @Test
    void testCrearReview_ClienteNoExiste() {
        // Preparar review de entrada con cliente que no existe
        Review entrada = new Review();
        Cliente cliente = new Cliente();
        cliente.setId(99L);
        entrada.setCliente(cliente);

        // Mock: el cliente no se encuentra en BD
        when(clienteService.buscarPorId(99L)).thenReturn(null);

        // Llamar al método del controlador
        ResponseEntity<Review> respuesta = controller.crearReview(entrada);

        // Verificar que devuelve 400 Bad Request
        assertEquals(400, respuesta.getStatusCode().value());

        // Verificar que NO se llama a guardar()
        verify(reviewService, never()).guardar(any());
    }

    @Test
    void testActualizarReview_Exito() {
        // Review existente
        Review existente = new Review();
        existente.setId(1L);
        existente.setDescripcion("Descripción Original");
        existente.setValoracion(3);

        Cliente cliente = new Cliente();
        cliente.setId(10L);
        existente.setCliente(cliente);

        // Review enviada para actualizar
        Review entrada = new Review();
        entrada.setDescripcion("Nueva descripción");
        entrada.setValoracion(5);

        // Config mock
        when(reviewService.buscarPorId(1L)).thenReturn(existente);
        when(reviewService.guardar(any(Review.class))).thenReturn(existente);

        ResponseEntity<Review> respuesta = controller.actualizarReview(1L, entrada);
        
        // Verificar
        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals("Nueva descripción", existente.getDescripcion());
        assertEquals(5, existente.getValoracion());
        assertEquals(cliente, existente.getCliente());

        // Verificar que se llamó a guardar()
        verify(reviewService).guardar(any(Review.class));
    }

}

package com.example.demo.controller;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Review;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(
	    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	    properties = {
	        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
	        "spring.datasource.driverClassName=org.h2.Driver",
	        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
	        "spring.jpa.hibernate.ddl-auto=create-drop"
	    }
	)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReviewRestControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private Cliente clienteGuardado;
    private Review reviewGuardada;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        clienteRepository.deleteAll();

        // Crear cliente 
        Cliente cliente = new Cliente();
        cliente.setNombre("Carlos");
        cliente.setEdad(25);
        cliente.setGenero("Masculino");
        cliente.setIntolerancia(false);

        clienteGuardado = clienteRepository.save(cliente);

        // Crear review 
        Review review = new Review();
        review.setDescripcion("Muy buen servicio");
        review.setValoracion(5);
        review.setCliente(clienteGuardado);

        reviewGuardada = reviewRepository.save(review);
    }

    // 1️º TEST E2E: GET /api/reviews/{id} → 200 y JSON correcto
    @Test
    @DisplayName("GET /api/reviews/{id} debe devolver 200 y la review en JSON")
    void obtenerReviewPorId_debeDevolver200YReview() throws Exception {

        mockMvc.perform(get("/api/reviews/{id}", reviewGuardada.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Campos de la review
                .andExpect(jsonPath("$.id", is(reviewGuardada.getId().intValue())))
                .andExpect(jsonPath("$.descripcion", is("Muy buen servicio")))
                .andExpect(jsonPath("$.valoracion", is(5)))
                // Cliente embebido
                .andExpect(jsonPath("$.cliente.id", is(clienteGuardado.getId().intValue())))
                .andExpect(jsonPath("$.cliente.nombre", is("Carlos")));
    }

    // 2️º TEST E2E: POST /api/reviews → 200 y JSON de la nueva review
    @Test
    @DisplayName("POST /api/reviews debe crear una review y devolver 200 con el JSON")
    void crearReview_debeDevolver200YReviewCreada() throws Exception {

        String jsonNuevaReview = """
                {
                  "descripcion": "Comida excelente",
                  "valoracion": 4,
                  "cliente": {
                    "id": %d
                  }
                }
                """.formatted(clienteGuardado.getId());

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNuevaReview)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.descripcion", is("Comida excelente")))
                .andExpect(jsonPath("$.valoracion", is(4)))
                .andExpect(jsonPath("$.cliente.id", is(clienteGuardado.getId().intValue())));
    }

    // Ejemplo de 404
    @Test
    @DisplayName("GET /api/reviews/{id} con id inexistente debe devolver 404")
    void obtenerReviewInexistente_debeDevolver404() throws Exception {

        mockMvc.perform(get("/api/reviews/{id}", 9999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

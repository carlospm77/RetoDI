package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Review;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testGuardarYBuscarReview() {
        // Crear y guardar un cliente
        Cliente cliente = new Cliente();
        cliente.setNombre("Carlos");
        cliente.setEdad(30);
        cliente.setGenero("Hombre");
        cliente.setIntolerancia(false);
        cliente.setDetalleIntolerancia(" ");
        cliente = clienteRepository.save(cliente);

        // Crear y guardar una review asociada
        Review review = new Review();
        review.setDescripcion("Excelente servicio");
        review.setValoracion(5);
        review.setCliente(cliente);

        Review guardada = reviewRepository.save(review);

        // Buscar la review por ID
        Optional<Review> encontradaOpt = reviewRepository.findById(guardada.getId());

        // Verificar resultados
        assertTrue(encontradaOpt.isPresent());
        Review encontrada = encontradaOpt.get();

        assertEquals("Excelente servicio", encontrada.getDescripcion());
        assertEquals(5, encontrada.getValoracion());
        assertEquals(cliente.getId(), encontrada.getCliente().getId());
    }
}

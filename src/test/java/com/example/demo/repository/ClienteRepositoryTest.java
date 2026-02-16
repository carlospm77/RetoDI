package com.example.demo.repository;

import com.example.demo.entity.Cliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClienteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Guardar y recuperar un cliente")
    void testGuardarYRecuperarCliente() {

        // Crear
        Cliente cliente = new Cliente();
        cliente.setNombre("Jorge Lopez");
        cliente.setEdad(20);
        cliente.setGenero("Masculino");
        cliente.setIntolerancia(true);
        cliente.setDetalleIntolerancia("Lactosa");

        entityManager.persist(cliente);
        entityManager.flush();

        // Buscar 
        Optional<Cliente> encontrado = clienteRepository.findById(cliente.getId());

        // Comprobar
        assertTrue(encontrado.isPresent());
        assertEquals("Jorge Lopez", encontrado.get().getNombre());
        assertEquals(20, encontrado.get().getEdad());
        assertEquals("Masculino", encontrado.get().getGenero());
        assertTrue(encontrado.get().isIntolerancia());
        assertEquals("Lactosa", encontrado.get().getDetalleIntolerancia());
    }

    @Test
    @DisplayName("Actualizar un cliente ")
    void testActualizarCliente() {

        // Crear(cliente original)
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana Gómez");
        cliente.setEdad(25);
        cliente.setGenero("Femenino");
        cliente.setIntolerancia(false);

        entityManager.persist(cliente);
        entityManager.flush();

        // Actualizar(cliente actualizado)
        cliente.setEdad(26);
        cliente.setIntolerancia(true);
        cliente.setDetalleIntolerancia("Gluten");

        Cliente actualizado = clienteRepository.save(cliente);

        // Comprobar
        assertEquals(26, actualizado.getEdad());
        assertTrue(actualizado.isIntolerancia());
        assertEquals("Gluten", actualizado.getDetalleIntolerancia());
    }

    @Test
    @DisplayName("Eliminar un cliente")
    void testEliminarCliente() {

        // Crear
        Cliente cliente = new Cliente();
        cliente.setNombre("Luis Torres");
        cliente.setEdad(40);
        cliente.setGenero("Masculino");

        entityManager.persist(cliente);
        entityManager.flush();

        Long id = cliente.getId();

        // Borrar por id
        clienteRepository.deleteById(id);

        // Comprobar
        Optional<Cliente> eliminado = clienteRepository.findById(id);
        assertFalse(eliminado.isPresent());
    }
}

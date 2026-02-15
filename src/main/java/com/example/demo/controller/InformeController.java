package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Cliente;
import com.example.demo.entity.Review;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ReviewService;

//Ruta base para acceder al dashboard de informes
@Controller
@RequestMapping("/informes") 
public class InformeController {

	// Servicio para obtener datos de clientes
    @Autowired
    private ClienteService clienteService; 

    // Servicio para obtener datos de reviews
    @Autowired
    private ReviewService reviewService; 

    @GetMapping
    public String informes(Model model) {

        // 1. CLIENTES POR GÉNERO
        // Se obtienen todos los clientes registrados
        List<Cliente> clientes = clienteService.mostrarTodos();

        // Se agrupan por género y se cuenta cuántos hay en cada categoría
        Map<String, Long> clientesPorGenero = clientes.stream()
                .collect(Collectors.groupingBy(Cliente::getGenero, Collectors.counting()));

        // Se envían las etiquetas (Hombre, Mujer, Otro) y los valores al modelo
        model.addAttribute("generosLabels", clientesPorGenero.keySet());
        model.addAttribute("generosData", clientesPorGenero.values());

        // 2. REVIEWS POR ESTRELLAS
        // Se obtienen todas las reviews
        List<Review> reviews = reviewService.mostrarTodas();

        // Se agrupan por valoración (1 a 5 estrellas)
        Map<Integer, Long> reviewsPorEstrellas = reviews.stream()
                .collect(Collectors.groupingBy(Review::getValoracion, Collectors.counting()));

        // Se asegura que siempre existan las 5 categorías, aunque alguna tenga 0
        int[] estrellas = {1, 2, 3, 4, 5};

        model.addAttribute("estrellasData",
                List.of(estrellas).stream()
                        .flatMapToInt(arr -> java.util.Arrays.stream(arr)) // Convierte el array en stream
                        .mapToObj(e -> reviewsPorEstrellas.getOrDefault(e, 0L)) // Obtiene el valor o 0 si no existe
                        .toList()
        );

        // 3. CLIENTES POR FRANJA DE EDAD
        // Se cuentan los clientes según rangos de edad definidos
        long edad0_15 = clientes.stream().filter(c -> c.getEdad() <= 15).count();
        long edad16_24 = clientes.stream().filter(c -> c.getEdad() >= 16 && c.getEdad() <= 24).count();
        long edad25_35 = clientes.stream().filter(c -> c.getEdad() >= 25 && c.getEdad() <= 35).count();
        long edad36_50 = clientes.stream().filter(c -> c.getEdad() >= 36 && c.getEdad() <= 50).count();
        long edad50mas = clientes.stream().filter(c -> c.getEdad() > 50).count();

        // Se envían etiquetas y valores al modelo
        model.addAttribute("edadLabels", List.of("0-15", "16-24", "25-35", "36-50", "50+"));
        model.addAttribute("edadData", List.of(edad0_15, edad16_24, edad25_35, edad36_50, edad50mas));

        // 4. INTOLERANCIA ALIMENTARIA
        // Se cuentan los clientes con intolerancia
        long conIntolerancia = clientes.stream().filter(Cliente::isIntolerancia).count();

        // Los que no tienen intolerancia son el resto
        long sinIntolerancia = clientes.size() - conIntolerancia;

        // Se envían los datos al modelo
        model.addAttribute("intoleranciaData", List.of(conIntolerancia, sinIntolerancia));

        // RETORNO DE LA VISTA
        // Se carga la plantilla Thymeleaf del dashboard de informes
        return "informes/dashboard";
    }
}

package com.example.demo.entity;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

=======
>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD
import jakarta.persistence.OneToMany;
=======
>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
import jakarta.persistence.OneToOne;

// Entidad Cliente
@Entity
public class Cliente {

	// Atributos
	
	// PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int edad;
    private String genero;
    private boolean intolerancia;

    @Column(name = "detalle_intolerancia")
    private String detalleIntolerancia;

    // Relación con Review
<<<<<<< HEAD
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
=======
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;

>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
    // getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean isIntolerancia() {
		return intolerancia;
	}

	public void setIntolerancia(boolean intolerancia) {
		this.intolerancia = intolerancia;
	}

	public String getDetalleIntolerancia() {
		return detalleIntolerancia;
	}

	public void setDetalleIntolerancia(String detalleIntolerancia) {
		this.detalleIntolerancia = detalleIntolerancia;
	}

<<<<<<< HEAD
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
=======
	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
    
}

package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Review review;

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

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}
    
}

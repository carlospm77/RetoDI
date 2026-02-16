package com.example.demo.entity;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

=======
>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
<<<<<<< HEAD
import jakarta.persistence.ManyToOne;
=======
>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
import jakarta.persistence.OneToOne;

// Entidad Review
@Entity
public class Review {

	// Atributos
	
	// PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String descripcion;
    private int valoracion; // 1 a 5

    // Relación con Cliente
<<<<<<< HEAD
    @ManyToOne 
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("reviews")
=======
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
>>>>>>> a2ca45a0249a2e6c98204ffbc159e78ae0678063
    private Cliente cliente;


    // getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}

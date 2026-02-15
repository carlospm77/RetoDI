package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Cliente;

//Indica que esta interfaz es un componente de acceso a datos
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> { 
	
}

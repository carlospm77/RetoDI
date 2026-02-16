package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> mostrarTodos() { 
    	return repo.findAll();
    	}
    
    public Cliente buscarPorId(Long id) {
    	return repo.findById(id).orElse(null);
    	}
    
    public Cliente guardar(Cliente c) {
    	return repo.save(c);
    	}
    
    public void eliminar(Long id) {
    	repo.deleteById(id); 
    	}
}


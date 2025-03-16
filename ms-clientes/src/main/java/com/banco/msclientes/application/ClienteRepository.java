package com.banco.msclientes.application;

import com.banco.msclientes.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
	Cliente save(Cliente cliente); // POST y PUT
	Optional<Cliente> findById(Long id); // GET por ID
	List<Cliente> findAll(); // GET todos
	Cliente update(Long id, Cliente cliente); // PUT (actualización completa)
	Cliente patch(Long id, Cliente clienteParcial); // PATCH (actualización parcial)
	void delete(Long id); // DELETE
}

package com.banco.msclientes.infrastructure.controller;

import com.banco.msclientes.application.ClienteRepository;
import com.banco.msclientes.domain.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	private final ClienteRepository clienteRepository;

	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> getAllClientes() {
		return ResponseEntity.ok(clienteRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
		return clienteRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
		Cliente nuevoCliente = clienteRepository.save(cliente);
		return ResponseEntity.ok(nuevoCliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
		Cliente updatedCliente = clienteRepository.update(id, cliente);
		return updatedCliente != null ? ResponseEntity.ok(updatedCliente) : ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Cliente> patchCliente(@PathVariable Long id, @RequestBody Cliente clienteParcial) {
		Cliente updatedCliente = clienteRepository.patch(id, clienteParcial);
		return updatedCliente != null ? ResponseEntity.ok(updatedCliente) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
		clienteRepository.delete(id);
		return ResponseEntity.noContent().build();
	}
}

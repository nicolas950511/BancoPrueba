package com.banco.mscuentas.infrastructure.controller;

import com.banco.mscuentas.application.CuentaService;
import com.banco.mscuentas.domain.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
	private final CuentaService cuentaService;

	public CuentaController(CuentaService cuentaService) {
		this.cuentaService = cuentaService;
	}

	@PostMapping
	public ResponseEntity<Cuenta> create(@RequestBody Cuenta cuenta) {
		return ResponseEntity.ok(cuentaService.save(cuenta));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cuenta> getById(@PathVariable Long id) {
		return cuentaService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Cuenta>> getAll() {
		return ResponseEntity.ok(cuentaService.findAll());
	}

	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<Cuenta>> getByCliente(@PathVariable Long clienteId) {
		return ResponseEntity.ok(cuentaService.findByClienteId(clienteId));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cuenta> update(@PathVariable Long id, @RequestBody Cuenta cuenta) {
		return cuentaService.findById(id)
				.map(existing -> ResponseEntity.ok(cuentaService.update(id, cuenta)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Cuenta> partialUpdate(@PathVariable Long id, @RequestBody Cuenta cuentaParcial) {
		return cuentaService.findById(id)
				.map(existing -> ResponseEntity.ok(cuentaService.patch(id, cuentaParcial)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (cuentaService.findById(id).isPresent()) {
			cuentaService.delete(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/transacciones")
	public ResponseEntity<Integer> obtenerCantidadTransacciones(@PathVariable Long id) {
		return cuentaService.findById(id)
				.map(cuenta -> ResponseEntity.ok(cuenta.getCantidadTransacciones()))
				.orElse(ResponseEntity.notFound().build());
	}
}

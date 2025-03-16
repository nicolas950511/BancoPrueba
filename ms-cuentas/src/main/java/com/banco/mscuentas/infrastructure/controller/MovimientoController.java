package com.banco.mscuentas.infrastructure.controller;

import com.banco.mscuentas.application.MovimientoService;
import com.banco.mscuentas.domain.Movimiento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {
	private final MovimientoService movimientoService;

	public MovimientoController(MovimientoService movimientoService) {
		this.movimientoService = movimientoService;
	}

	@PostMapping
	public ResponseEntity<Movimiento> registrarMovimiento(@RequestBody Movimiento movimiento) {
		return ResponseEntity.ok(movimientoService.registrarMovimiento(movimiento));
	}

	@GetMapping("/{cuentaId}")
	public ResponseEntity<List<Movimiento>> obtenerMovimientos(@PathVariable Long cuentaId) {
		return ResponseEntity.ok(movimientoService.obtenerMovimientosPorCuenta(cuentaId));
	}
}

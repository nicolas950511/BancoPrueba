package com.banco.mscuentas.infrastructure.controller;

import com.banco.mscuentas.application.ReporteService;
import com.banco.mscuentas.domain.ReporteEstadoCuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
	private final ReporteService reporteService;

	public ReporteController(ReporteService reporteService) {
		this.reporteService = reporteService;
	}

	@GetMapping
	public ResponseEntity<List<ReporteEstadoCuenta>> generarReporte(
			@RequestParam Long clienteId,
			@RequestParam String fechaInicio,
			@RequestParam String fechaFin) {

		LocalDate inicio = LocalDate.parse(fechaInicio);
		LocalDate fin = LocalDate.parse(fechaFin);

		return ResponseEntity.ok(reporteService.generarReporte(clienteId, inicio, fin));
	}
}

package com.banco.mscuentas.infrastructure.controller;

import com.banco.mscuentas.application.ReporteService;
import com.banco.mscuentas.domain.ReporteEstadoCuenta;
import com.banco.mscuentas.domain.ReporteEstadoCuenta.MovimientoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteControllerTest {

	@Mock
	private ReporteService reporteService;

	@InjectMocks
	private ReporteController reporteController;

	private ReporteEstadoCuenta reporte1;
	private ReporteEstadoCuenta reporte2;

	@BeforeEach
	void setUp() {
		MovimientoDTO movimiento1 = new MovimientoDTO(LocalDateTime.now(), "Depósito", 200.0, 5000.0);
		MovimientoDTO movimiento2 = new MovimientoDTO(LocalDateTime.now(), "Retiro", -100.0, 4900.0);

		reporte1 = new ReporteEstadoCuenta(1L, "Juan Pérez", "123456789", "1001", 5000.0, List.of(movimiento1));
		reporte2 = new ReporteEstadoCuenta(2L, "Ana Gómez", "987654321", "2002", 8000.0, List.of(movimiento2));
	}

	@Test
	void generarReporte_ClienteConMovimientos_DebeRetornarListaDeReportes() {
		Long clienteId = 1L;
		LocalDate fechaInicio = LocalDate.of(2024, 3, 1);
		LocalDate fechaFin = LocalDate.of(2024, 3, 31);

		List<ReporteEstadoCuenta> reportes = Arrays.asList(reporte1, reporte2);
		when(reporteService.generarReporte(clienteId, fechaInicio, fechaFin)).thenReturn(reportes);

		ResponseEntity<List<ReporteEstadoCuenta>> response = reporteController.generarReporte(
				clienteId, fechaInicio.toString(), fechaFin.toString()
		);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		assertEquals("Juan Pérez", response.getBody().get(0).getNombreCliente());
		assertEquals("Ana Gómez", response.getBody().get(1).getNombreCliente());
	}

	@Test
	void generarReporte_ClienteSinMovimientos_DebeRetornarListaVacia() {
		Long clienteId = 99L;
		LocalDate fechaInicio = LocalDate.of(2024, 3, 1);
		LocalDate fechaFin = LocalDate.of(2024, 3, 31);

		when(reporteService.generarReporte(clienteId, fechaInicio, fechaFin)).thenReturn(List.of());

		ResponseEntity<List<ReporteEstadoCuenta>> response = reporteController.generarReporte(
				clienteId, fechaInicio.toString(), fechaFin.toString()
		);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
	}

	@Test
	void generarReporte_FechasInvalidas_DebeLanzarExcepcion() {
		Long clienteId = 1L;

		Exception exception = assertThrows(Exception.class, () -> {
			reporteController.generarReporte(clienteId, "fecha-mal-formato", "2024-03-31");
		});

		assertTrue(exception instanceof RuntimeException);
	}
}

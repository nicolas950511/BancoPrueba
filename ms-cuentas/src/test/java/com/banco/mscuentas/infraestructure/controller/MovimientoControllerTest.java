package com.banco.mscuentas.infraestructure.controller;

import com.banco.mscuentas.application.MovimientoService;
import com.banco.mscuentas.domain.Movimiento;
import com.banco.mscuentas.infrastructure.controller.MovimientoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovimientoControllerTest {

	@Mock
	private MovimientoService movimientoService;

	@InjectMocks
	private MovimientoController movimientoController;

	private Movimiento movimiento1;
	private Movimiento movimiento2;

	@BeforeEach
	void setUp() {
		movimiento1 = new Movimiento(1L, LocalDateTime.now(), "Depósito", 100.0, 6000.0, 1L);
		movimiento2 = new Movimiento(2L, LocalDateTime.now(), "Retiro", -50.0, 5950.0, 1L);
	}

	@Test
	void registrarMovimiento_MovimientoValido_DebeRetornarMovimientoRegistrado() {
		when(movimientoService.registrarMovimiento(movimiento1)).thenReturn(movimiento1);

		ResponseEntity<Movimiento> response = movimientoController.registrarMovimiento(movimiento1);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals("Depósito", response.getBody().getTipoMovimiento());
		assertEquals(100.0, response.getBody().getValor());
		assertEquals(1L, response.getBody().getCuentaId());
	}

	@Test
	void registrarMovimiento_MovimientoNulo_DebeRetornarError() {
		when(movimientoService.registrarMovimiento(null)).thenThrow(new IllegalArgumentException("Movimiento no puede ser nulo"));

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			movimientoController.registrarMovimiento(null);
		});

		assertEquals("Movimiento no puede ser nulo", exception.getMessage());
	}

	@Test
	void obtenerMovimientos_CuentaTieneMovimientos_DebeRetornarListaMovimientos() {
		List<Movimiento> movimientos = Arrays.asList(movimiento1, movimiento2);
		when(movimientoService.obtenerMovimientosPorCuenta(1L)).thenReturn(movimientos);

		ResponseEntity<List<Movimiento>> response = movimientoController.obtenerMovimientos(1L);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().size());
		assertEquals("Depósito", response.getBody().get(0).getTipoMovimiento());
		assertEquals("Retiro", response.getBody().get(1).getTipoMovimiento());
	}

	@Test
	void obtenerMovimientos_CuentaNoTieneMovimientos_DebeRetornarListaVacia() {
		when(movimientoService.obtenerMovimientosPorCuenta(99L)).thenReturn(List.of());

		ResponseEntity<List<Movimiento>> response = movimientoController.obtenerMovimientos(99L);

		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
	}
}

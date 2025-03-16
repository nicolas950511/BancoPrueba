package com.banco.mscuentas.infrastructure.controller;

import com.banco.mscuentas.application.CuentaService;
import com.banco.mscuentas.domain.Cuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaControllerTest {

	@Mock
	private CuentaService cuentaService;

	@InjectMocks
	private CuentaController cuentaController;

	private Cuenta cuenta;

	@BeforeEach
	void setUp() {
		cuenta = new Cuenta(1L, "1234567890", "Ahorros", 5000.0, true, 1001L, 10);
	}

	@Test
	void create_CuentaValida_DebeRetornarCuentaCreada() {
		when(cuentaService.save(cuenta)).thenReturn(cuenta);

		ResponseEntity<Cuenta> response = cuentaController.create(cuenta);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("1234567890", response.getBody().getNumeroCuenta());
	}

	@Test
	void getById_CuentaExiste_DebeRetornarCuenta() {
		when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));

		ResponseEntity<Cuenta> response = cuentaController.getById(1L);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("1234567890", response.getBody().getNumeroCuenta());
	}

	@Test
	void getById_CuentaNoExiste_DebeRetornarNotFound() {
		when(cuentaService.findById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Cuenta> response = cuentaController.getById(99L);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void getAll_DebeRetornarListaDeCuentas() {
		List<Cuenta> cuentas = Arrays.asList(
				cuenta, new Cuenta(2L, "0987654321", "Corriente", 8000.0, true, 1002L, 5)
		);
		when(cuentaService.findAll()).thenReturn(cuentas);

		ResponseEntity<List<Cuenta>> response = cuentaController.getAll();

		assertEquals(2, response.getBody().size());
		assertEquals("1234567890", response.getBody().get(0).getNumeroCuenta());
	}

	@Test
	void getAll_SinCuentas_DebeRetornarListaVacia() {
		when(cuentaService.findAll()).thenReturn(List.of());

		ResponseEntity<List<Cuenta>> response = cuentaController.getAll();

		assertTrue(response.getBody().isEmpty());
	}

	@Test
	void getByCliente_ClienteTieneCuentas_DebeRetornarListaCuentas() {
		List<Cuenta> cuentas = Arrays.asList(cuenta);
		when(cuentaService.findByClienteId(1001L)).thenReturn(cuentas);

		ResponseEntity<List<Cuenta>> response = cuentaController.getByCliente(1001L);

		assertEquals(1, response.getBody().size());
		assertEquals("1234567890", response.getBody().get(0).getNumeroCuenta());
	}

	@Test
	void getByCliente_ClienteNoTieneCuentas_DebeRetornarListaVacia() {
		when(cuentaService.findByClienteId(2000L)).thenReturn(List.of());

		ResponseEntity<List<Cuenta>> response = cuentaController.getByCliente(2000L);

		assertTrue(response.getBody().isEmpty());
	}

	@Test
	void update_CuentaExiste_DebeActualizarCuenta() {
		when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));
		when(cuentaService.update(eq(1L), any(Cuenta.class))).thenReturn(cuenta);

		ResponseEntity<Cuenta> response = cuentaController.update(1L, cuenta);

		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void update_CuentaNoExiste_DebeRetornarNotFound() {
		when(cuentaService.findById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Cuenta> response = cuentaController.update(99L, cuenta);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void partialUpdate_CuentaExiste_DebeActualizarParcialmente() {
		when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));
		when(cuentaService.patch(eq(1L), any(Cuenta.class))).thenReturn(cuenta);

		ResponseEntity<Cuenta> response = cuentaController.partialUpdate(1L, cuenta);

		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void partialUpdate_CuentaNoExiste_DebeRetornarNotFound() {
		when(cuentaService.findById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Cuenta> response = cuentaController.partialUpdate(99L, cuenta);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void delete_CuentaExiste_DebeEliminarCuenta() {
		when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));
		doNothing().when(cuentaService).delete(1L);

		ResponseEntity<Void> response = cuentaController.delete(1L);

		assertEquals(204, response.getStatusCodeValue());
		verify(cuentaService, times(1)).delete(1L);
	}

	@Test
	void delete_CuentaNoExiste_DebeRetornarNotFound() {
		when(cuentaService.findById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Void> response = cuentaController.delete(99L);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void obtenerCantidadTransacciones_CuentaExiste_DebeRetornarCantidadTransacciones() {
		when(cuentaService.findById(1L)).thenReturn(Optional.of(cuenta));

		ResponseEntity<Integer> response = cuentaController.obtenerCantidadTransacciones(1L);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(10, response.getBody());
	}

	@Test
	void obtenerCantidadTransacciones_CuentaNoExiste_DebeRetornarNotFound() {
		when(cuentaService.findById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Integer> response = cuentaController.obtenerCantidadTransacciones(99L);

		assertEquals(404, response.getStatusCodeValue());
	}
}

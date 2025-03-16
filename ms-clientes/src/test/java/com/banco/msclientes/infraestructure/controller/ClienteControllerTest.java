package com.banco.msclientes.infrastructure.controller;

import com.banco.msclientes.application.ClienteRepository;
import com.banco.msclientes.domain.Cliente;
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
class ClienteControllerTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteController clienteController;

	private Cliente cliente;

	@BeforeEach
	void setUp() {
		cliente = new Cliente("Juan Pérez", "Masculino", 30, "123456789", "Calle 123", "321654987", 1L, "password", true);
	}

	@Test
	void getAllClientes_DebeRetornarListaClientes() {
		List<Cliente> clientes = Arrays.asList(cliente, new Cliente("Maria Lopez", "Femenino", 25, "987654321", "Calle 456", "987654321", 2L, "pass", true));
		when(clienteRepository.findAll()).thenReturn(clientes);

		ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

		assertEquals(2, response.getBody().size());
		assertEquals("Juan Pérez", response.getBody().get(0).getNombre());
	}

	@Test
	void getAllClientes_DebeRetornarListaVaciaSiNoHayClientes() {
		when(clienteRepository.findAll()).thenReturn(List.of());

		ResponseEntity<List<Cliente>> response = clienteController.getAllClientes();

		assertNotNull(response.getBody());
		assertTrue(response.getBody().isEmpty());
	}

	@Test
	void getCliente_ClienteExiste_DebeRetornarCliente() {
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

		ResponseEntity<Cliente> response = clienteController.getCliente(1L);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Juan Pérez", response.getBody().getNombre());
	}

	@Test
	void getCliente_ClienteNoExiste_DebeRetornarNotFound() {
		when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Cliente> response = clienteController.getCliente(99L);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void create_ClienteValido_DebeRetornarClienteCreado() {
		when(clienteRepository.save(cliente)).thenReturn(cliente);

		ResponseEntity<Cliente> response = clienteController.create(cliente);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Juan Pérez", response.getBody().getNombre());
	}

	@Test
	void updateCliente_ClienteExiste_DebeActualizarCliente() {
		when(clienteRepository.update(eq(1L), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<Cliente> response = clienteController.updateCliente(1L, cliente);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("Juan Pérez", response.getBody().getNombre());
	}

	@Test
	void updateCliente_ClienteNoExiste_DebeRetornarNotFound() {
		when(clienteRepository.update(eq(99L), any(Cliente.class))).thenReturn(null);

		ResponseEntity<Cliente> response = clienteController.updateCliente(99L, cliente);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void patchCliente_ClienteExiste_DebeActualizarParcialmenteCliente() {
		when(clienteRepository.patch(eq(1L), any(Cliente.class))).thenReturn(cliente);

		ResponseEntity<Cliente> response = clienteController.patchCliente(1L, cliente);

		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	void patchCliente_ClienteNoExiste_DebeRetornarNotFound() {
		when(clienteRepository.patch(eq(99L), any(Cliente.class))).thenReturn(null);

		ResponseEntity<Cliente> response = clienteController.patchCliente(99L, cliente);

		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	void deleteCliente_ClienteExiste_DebeEliminarCliente() {
		doNothing().when(clienteRepository).delete(1L);

		ResponseEntity<Void> response = clienteController.deleteCliente(1L);

		assertEquals(204, response.getStatusCodeValue());
		verify(clienteRepository, times(1)).delete(1L);
	}

	@Test
	void deleteCliente_ClienteNoExiste_DebeRetornarNoContent() {
		doNothing().when(clienteRepository).delete(99L);

		ResponseEntity<Void> response = clienteController.deleteCliente(99L);

		assertEquals(204, response.getStatusCodeValue());
	}
}

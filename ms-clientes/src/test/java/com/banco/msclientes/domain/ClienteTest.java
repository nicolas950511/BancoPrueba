package com.banco.msclientes.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

	@Test
	void crearCliente_ConDatosValidos_DebeInicializarCorrectamente() {
		String nombre = "Juan Pérez";
		String genero = "Masculino";
		int edad = 30;
		String identificacion = "123456789";
		String direccion = "Calle 123, Bogotá";
		String telefono = "321654987";
		Long clienteId = 1001L;
		String contraseña = "secreta123";
		boolean estado = true;

		Cliente cliente = new Cliente(nombre, genero, edad, identificacion, direccion, telefono, clienteId, contraseña, estado);

		assertEquals(nombre, cliente.getNombre());
		assertEquals(genero, cliente.getGenero());
		assertEquals(edad, cliente.getEdad());
		assertEquals(identificacion, cliente.getIdentificacion());
		assertEquals(direccion, cliente.getDireccion());
		assertEquals(telefono, cliente.getTelefono());
		assertEquals(clienteId, cliente.getClienteId());
		assertEquals(contraseña, cliente.getContraseña());
		assertTrue(cliente.isEstado());
	}

	@Test
	void modificarCliente_DebeActualizarDatosCorrectamente() {
		Cliente cliente = new Cliente("Juan Pérez", "Masculino", 30, "123456789", "Calle 123, Bogotá", "321654987", 1001L, "secreta123", true);
		cliente.setNombre("Carlos Gómez");
		cliente.setDireccion("Avenida 45, Medellín");
		cliente.setEstado(false);
		assertEquals("Carlos Gómez", cliente.getNombre());
		assertEquals("Avenida 45, Medellín", cliente.getDireccion());
		assertFalse(cliente.isEstado()); // Ahora debe ser false
	}
}

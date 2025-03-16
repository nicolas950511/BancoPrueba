package com.banco.mscuentas.domain.exception;

public class ClienteNotFoundException extends RuntimeException {
	public ClienteNotFoundException(Long clienteId) {
		super("Cliente con ID " + clienteId + " no encontrado.");
	}
}

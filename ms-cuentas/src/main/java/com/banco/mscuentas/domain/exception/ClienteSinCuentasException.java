package com.banco.mscuentas.domain.exception;

public class ClienteSinCuentasException extends RuntimeException {
	public ClienteSinCuentasException(Long clienteId) {
		super("El cliente con ID " + clienteId + " no tiene cuentas registradas.");
	}
}

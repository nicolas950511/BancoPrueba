package com.banco.mscuentas.domain.exception;

public class NumeroCuentaDuplicadoException extends RuntimeException {
	public NumeroCuentaDuplicadoException(String numeroCuenta) {
		super("El número de cuenta " + numeroCuenta + " ya está registrado.");
	}
}

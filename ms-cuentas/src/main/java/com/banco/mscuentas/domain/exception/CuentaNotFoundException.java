package com.banco.mscuentas.domain.exception;

public class CuentaNotFoundException extends RuntimeException {
	public CuentaNotFoundException(Long cuentaId) {
		super("Cuenta con ID " + cuentaId + " no encontrada.");
	}
}

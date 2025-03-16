package com.banco.mscuentas.domain.exception;

public class SaldoInsuficienteException extends RuntimeException {
	public SaldoInsuficienteException(String message) {
		super(message);
	}
}

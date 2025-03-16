package com.banco.mscuentas.infrastructure.exception;

import com.banco.mscuentas.domain.exception.CuentaNotFoundException;
import com.banco.mscuentas.domain.exception.NumeroCuentaDuplicadoException;
import com.banco.mscuentas.domain.exception.SaldoInsuficienteException;
import com.banco.mscuentas.domain.exception.ClienteNotFoundException;
import com.banco.mscuentas.domain.exception.ClienteSinCuentasException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<Map<String, Object>> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
		return buildErrorResponse(HttpStatus.BAD_REQUEST, "Saldo insuficiente", ex.getMessage());
	}

	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleClienteNotFoundException(ClienteNotFoundException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, "Cliente no encontrado", ex.getMessage());
	}

	@ExceptionHandler(ClienteSinCuentasException.class)
	public ResponseEntity<Map<String, Object>> handleClienteSinCuentasException(ClienteSinCuentasException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, "Cliente sin cuentas", ex.getMessage());
	}

	@ExceptionHandler(CuentaNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleCuentaNotFoundException(CuentaNotFoundException ex) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, "Cuenta no encontrada", ex.getMessage());
	}

	@ExceptionHandler(NumeroCuentaDuplicadoException.class)
	public ResponseEntity<Map<String, Object>> handleNumeroCuentaDuplicadoException(NumeroCuentaDuplicadoException ex) {
		return buildErrorResponse(HttpStatus.CONFLICT, "Número de cuenta duplicado", ex.getMessage());
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String error, String message) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("timestamp", LocalDateTime.now());
		errorResponse.put("status", status.value());
		errorResponse.put("error", error);
		errorResponse.put("message", message);

		return new ResponseEntity<>(errorResponse, status);
	}
}

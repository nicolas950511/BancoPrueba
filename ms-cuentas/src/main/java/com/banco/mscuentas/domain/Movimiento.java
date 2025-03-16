package com.banco.mscuentas.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Movimiento {
	private Long id;
	private LocalDateTime fecha;
	private String tipoMovimiento;
	private Double valor;
	private Double saldo;
	private Long cuentaId;

	public Movimiento(Long id, LocalDateTime fecha, String tipoMovimiento, Double valor, Double saldo,
					  Long cuentaId) {
		this.id=id;
		this.fecha = fecha;
		this.tipoMovimiento = tipoMovimiento;
		this.valor = valor;
		this.saldo = saldo;
		this.cuentaId = cuentaId;
	}
}

package com.banco.mscuentas.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReporteEstadoCuenta {
	private Long clienteId;
	private String nombreCliente;
	private String identificacion;
	private String numeroCuenta;
	private Double saldoActual;
	private List<MovimientoDTO> movimientos;

	public ReporteEstadoCuenta(Long clienteId, String nombreCliente, String identificacion, String numeroCuenta,
							   Double saldoActual,
							   List<MovimientoDTO> movimientos) {
		this.clienteId = clienteId;
		this.numeroCuenta = numeroCuenta;
		this.saldoActual = saldoActual;
		this.movimientos = movimientos;
		this.nombreCliente=nombreCliente;
		this.identificacion=identificacion;
	}

	@Getter
	@Setter
	public static class MovimientoDTO {
		private LocalDateTime fecha;
		private String tipoMovimiento;
		private Double valor;
		private Double saldo;

		public MovimientoDTO(LocalDateTime fecha, String tipoMovimiento, Double valor, Double saldo) {
			this.fecha = fecha;
			this.tipoMovimiento = tipoMovimiento;
			this.valor = valor;
			this.saldo = saldo;
		}
	}
}

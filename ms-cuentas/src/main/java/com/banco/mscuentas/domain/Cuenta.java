package com.banco.mscuentas.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cuenta {
	private Long id;
	private String numeroCuenta;
	private String tipoCuenta;
	private Double saldoInicial;
	private Boolean estado;
	private Long clienteId;
	private Integer cantidadTransacciones;

	public Cuenta(Long id, String numeroCuenta, String tipoCuenta, Double saldoInicial, Boolean estado,
				  Long clienteId, Integer cantidadTransacciones) {
		this.id=id;
		this.numeroCuenta = numeroCuenta;
		this.tipoCuenta = tipoCuenta;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
		this.clienteId = clienteId;
		this.cantidadTransacciones = cantidadTransacciones;
	}
}

package com.banco.msclientes.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Cliente extends Persona {
	private Long clienteId;
	private String contraseña;
	private boolean estado;

	public Cliente(String nombre, String genero, int edad, String identificacion,
				   String direccion, String telefono, Long clienteId, String contraseña, boolean estado) {
		super(nombre, genero, edad, identificacion, direccion, telefono);
		this.clienteId = clienteId;
		this.contraseña = contraseña;
		this.estado = estado;
	}
}

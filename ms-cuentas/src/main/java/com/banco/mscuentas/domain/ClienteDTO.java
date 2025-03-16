package com.banco.mscuentas.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {
	private Long id;
	private String nombre;
	private String identificacion;

	public ClienteDTO() {}

	public ClienteDTO(Long id, String nombre, String identificacion) {
		this.id = id;
		this.nombre = nombre;
		this.identificacion = identificacion;
	}
}

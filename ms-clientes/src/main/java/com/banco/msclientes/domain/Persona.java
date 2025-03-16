package com.banco.msclientes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona {
	private String nombre;
	private String genero;
	private int edad;
	private String identificacion;
	private String direccion;
	private String telefono;
}

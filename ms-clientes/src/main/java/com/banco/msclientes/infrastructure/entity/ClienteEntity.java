package com.banco.msclientes.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class ClienteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clienteId;

	private String nombre;
	private String genero;
	private int edad;
	private String identificacion;
	private String direccion;
	private String telefono;
	private String contraseña;
	private boolean estado;
}

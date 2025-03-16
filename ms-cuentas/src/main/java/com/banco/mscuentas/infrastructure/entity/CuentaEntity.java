package com.banco.mscuentas.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cuentas")
public class CuentaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String numeroCuenta;

	@Column(nullable = false)
	private String tipoCuenta;

	@Column(nullable = false)
	private Double saldoInicial;

	@Column(nullable = false)
	private Boolean estado;

	@Column(nullable = false)
	private Long clienteId;

	@Column(nullable = false, columnDefinition = "int default 0")
	private Integer cantidadTransacciones = 0;

	@OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<MovimientoEntity> movimientos;
}

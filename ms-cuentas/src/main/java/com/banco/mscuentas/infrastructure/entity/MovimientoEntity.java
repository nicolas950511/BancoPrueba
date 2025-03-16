package com.banco.mscuentas.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "movimientos")
public class MovimientoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime fecha;

	@Column(nullable = false)
	private String tipoMovimiento;

	@Column(nullable = false)
	private Double valor;

	@Column(nullable = false)
	private Double saldo;

	@ManyToOne
	@JoinColumn(name = "cuenta_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE) // 🔹 Asegura eliminación en cascada en PostgreSQL
	private CuentaEntity cuenta;
}

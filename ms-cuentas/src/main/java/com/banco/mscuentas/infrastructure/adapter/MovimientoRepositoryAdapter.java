package com.banco.mscuentas.infrastructure.adapter;

import com.banco.mscuentas.application.MovimientoService;
import com.banco.mscuentas.domain.Movimiento;
import com.banco.mscuentas.domain.exception.CuentaNotFoundException;
import com.banco.mscuentas.domain.exception.SaldoInsuficienteException;
import com.banco.mscuentas.infrastructure.entity.CuentaEntity;
import com.banco.mscuentas.infrastructure.entity.MovimientoEntity;
import com.banco.mscuentas.infrastructure.repository.JpaCuentaRepository;
import com.banco.mscuentas.infrastructure.repository.JpaMovimientoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovimientoRepositoryAdapter implements MovimientoService {
	private final JpaMovimientoRepository movimientoRepository;
	private final JpaCuentaRepository cuentaRepository;

	public MovimientoRepositoryAdapter(JpaMovimientoRepository movimientoRepository, JpaCuentaRepository cuentaRepository) {
		this.movimientoRepository = movimientoRepository;
		this.cuentaRepository = cuentaRepository;
	}

	@Transactional
	public Movimiento registrarMovimiento(Movimiento movimiento) {
		// 🔹 Buscar la cuenta y lanzar excepción si no existe
		CuentaEntity cuenta = cuentaRepository.findById(movimiento.getCuentaId())
				.orElseThrow(() -> new CuentaNotFoundException(movimiento.getCuentaId()));

		// 🔹 Calcular el nuevo saldo
		Double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();

		// 🔹 Validar si el retiro es posible
		if (nuevoSaldo < 0) {
			throw new SaldoInsuficienteException("Saldo no disponible");
		}

		// 🔹 Guardar el movimiento en la base de datos
		MovimientoEntity movimientoEntity = new MovimientoEntity();
		movimientoEntity.setCuenta(cuenta);
		movimientoEntity.setFecha(LocalDateTime.now());
		movimientoEntity.setTipoMovimiento(movimiento.getTipoMovimiento());
		movimientoEntity.setValor(movimiento.getValor());
		movimientoEntity.setSaldo(nuevoSaldo);

		movimientoEntity = movimientoRepository.save(movimientoEntity);

		// 🔹 Actualizar la cuenta con el nuevo saldo y transacciones
		cuenta.setSaldoInicial(nuevoSaldo);
		cuenta.setCantidadTransacciones(cuenta.getCantidadTransacciones() + 1);
		cuentaRepository.save(cuenta);

		return mapToDomain(movimientoEntity);
	}

	@Override
	public List<Movimiento> obtenerMovimientosPorCuenta(Long cuentaId) {
		return movimientoRepository.findByCuentaId(cuentaId).stream()
				.map(this::mapToDomain)
				.collect(Collectors.toList());
	}

	private Movimiento mapToDomain(MovimientoEntity entity) {
		return new Movimiento(
				entity.getId(),
				entity.getFecha(),
				entity.getTipoMovimiento(),
				entity.getValor(),
				entity.getSaldo(),
				entity.getCuenta().getId()
		);
	}
}

package com.banco.mscuentas.infrastructure.adapter;

import com.banco.mscuentas.application.CuentaService;
import com.banco.mscuentas.domain.Cuenta;
import com.banco.mscuentas.domain.exception.ClienteNotFoundException;
import com.banco.mscuentas.domain.exception.NumeroCuentaDuplicadoException;
import com.banco.mscuentas.infrastructure.entity.CuentaEntity;
import com.banco.mscuentas.infrastructure.repository.JpaCuentaRepository;
import com.banco.mscuentas.infrastructure.repository.JpaMovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CuentaRepositoryAdapter implements CuentaService {
	private final JpaCuentaRepository repository;
	private final JpaMovimientoRepository movimientoRepository;
	private final ClienteRepositoryAdapter clienteRepositoryAdapter;

	public CuentaRepositoryAdapter(JpaMovimientoRepository movimientoRepository,
								   JpaCuentaRepository repository
			, ClienteRepositoryAdapter clienteRepositoryAdapter) {
		this.repository = repository;
		this.movimientoRepository = movimientoRepository;
		this.clienteRepositoryAdapter=clienteRepositoryAdapter;

	}

	@Override
	public Cuenta save(Cuenta cuenta) {
		if (!clienteRepositoryAdapter.clienteExiste(cuenta.getClienteId())) {
			throw new ClienteNotFoundException(cuenta.getClienteId());
		}
		if (repository.findByNumeroCuenta(cuenta.getNumeroCuenta()).isPresent()) {
			throw new NumeroCuentaDuplicadoException(cuenta.getNumeroCuenta());
		}

		CuentaEntity entity = mapToEntity(cuenta);
		return mapToDomain(repository.save(entity));
	}

	@Override
	public Optional<Cuenta> findById(Long id) {
		return repository.findById(id).map(this::mapToDomain);
	}

	@Override
	public List<Cuenta> findAll() {
		return repository.findAll().stream().map(this::mapToDomain).collect(Collectors.toList());
	}

	@Override
	public List<Cuenta> findByClienteId(Long clienteId) {
		return repository.findByClienteId(clienteId).stream().map(this::mapToDomain).collect(Collectors.toList());
	}

	@Override
	public Cuenta update(Long id, Cuenta cuenta) {
		CuentaEntity entity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

		entity.setTipoCuenta(cuenta.getTipoCuenta());
		entity.setSaldoInicial(cuenta.getSaldoInicial());
		entity.setEstado(cuenta.getEstado());
		entity.setClienteId(cuenta.getClienteId());

		return mapToDomain(repository.save(entity));
	}

	@Override
	public Cuenta patch(Long id, Cuenta cuentaParcial) {
		CuentaEntity entity = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

		if (cuentaParcial.getTipoCuenta() != null) entity.setTipoCuenta(cuentaParcial.getTipoCuenta());
		if (cuentaParcial.getSaldoInicial() != null) entity.setSaldoInicial(cuentaParcial.getSaldoInicial());
		if (cuentaParcial.getEstado() != null) entity.setEstado(cuentaParcial.getEstado());
		if (cuentaParcial.getClienteId() != null) entity.setClienteId(cuentaParcial.getClienteId());

		return mapToDomain(repository.save(entity));
	}

	@Override
	@Transactional
	public void delete(Long id) {
		CuentaEntity cuenta = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
		movimientoRepository.deleteByCuentaId(id);
		repository.deleteById(id);
	}


	private CuentaEntity mapToEntity(Cuenta cuenta) {
		CuentaEntity entity = new CuentaEntity();
		entity.setId(cuenta.getId());
		entity.setNumeroCuenta(cuenta.getNumeroCuenta());
		entity.setTipoCuenta(cuenta.getTipoCuenta());
		entity.setSaldoInicial(cuenta.getSaldoInicial());
		entity.setEstado(cuenta.getEstado());
		entity.setClienteId(cuenta.getClienteId());
		return entity;
	}

	private Cuenta mapToDomain(CuentaEntity entity) {
		return new Cuenta(entity.getId(), entity.getNumeroCuenta(), entity.getTipoCuenta(),
				entity.getSaldoInicial(),
				entity.getEstado(), entity.getClienteId(), entity.getCantidadTransacciones());
	}
}

package com.banco.mscuentas.infrastructure.repository;

import com.banco.mscuentas.infrastructure.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JpaCuentaRepository extends JpaRepository<CuentaEntity, Long> {
	List<CuentaEntity> findByClienteId(Long clienteId);

	Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
}

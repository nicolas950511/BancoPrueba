package com.banco.mscuentas.infrastructure.repository;

import com.banco.mscuentas.infrastructure.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JpaCuentaRepository extends JpaRepository<CuentaEntity, Long> {
	List<CuentaEntity> findByClienteId(Long clienteId);
}

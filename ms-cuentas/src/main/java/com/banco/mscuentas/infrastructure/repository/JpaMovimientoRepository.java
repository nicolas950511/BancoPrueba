package com.banco.mscuentas.infrastructure.repository;

import com.banco.mscuentas.infrastructure.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaMovimientoRepository extends JpaRepository<MovimientoEntity, Long> {

	List<MovimientoEntity> findByCuentaId(Long cuentaId);

	@Query("SELECT m FROM MovimientoEntity m WHERE m.cuenta.id = :cuentaId AND m.fecha BETWEEN :fechaInicio AND :fechaFin")
	List<MovimientoEntity> findByCuentaIdAndFechaBetween(
			@Param("cuentaId") Long cuentaId,
			@Param("fechaInicio")
			LocalDateTime fechaInicio,
			@Param("fechaFin") LocalDateTime fechaFin
	);

	@Modifying
	@Query("DELETE FROM MovimientoEntity m WHERE m.cuenta.id = :cuentaId")
	void deleteByCuentaId(@Param("cuentaId") Long cuentaId);

}

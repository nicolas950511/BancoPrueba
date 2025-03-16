package com.banco.mscuentas.application;

import com.banco.mscuentas.domain.Cuenta;
import java.util.List;
import java.util.Optional;

public interface CuentaService {
	Cuenta save(Cuenta cuenta);
	Optional<Cuenta> findById(Long id);
	List<Cuenta> findAll();
	List<Cuenta> findByClienteId(Long clienteId);
	Cuenta update(Long id, Cuenta cuenta);
	Cuenta patch(Long id, Cuenta cuentaParcial);
	void delete(Long id);
}

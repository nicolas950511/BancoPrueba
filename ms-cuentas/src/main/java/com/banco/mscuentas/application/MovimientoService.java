package com.banco.mscuentas.application;

import com.banco.mscuentas.domain.Movimiento;
import java.util.List;

public interface MovimientoService {
	Movimiento registrarMovimiento(Movimiento movimiento);
	List<Movimiento> obtenerMovimientosPorCuenta(Long cuentaId);
}

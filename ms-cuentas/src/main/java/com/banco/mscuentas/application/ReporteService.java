package com.banco.mscuentas.application;

import com.banco.mscuentas.domain.ReporteEstadoCuenta;
import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
	List<ReporteEstadoCuenta> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin);
}

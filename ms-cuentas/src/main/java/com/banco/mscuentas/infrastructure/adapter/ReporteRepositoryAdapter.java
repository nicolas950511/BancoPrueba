package com.banco.mscuentas.infrastructure.adapter;

import com.banco.mscuentas.application.ReporteService;
import com.banco.mscuentas.domain.ClienteDTO;
import com.banco.mscuentas.domain.ReporteEstadoCuenta;
import com.banco.mscuentas.domain.exception.ClienteSinCuentasException;
import com.banco.mscuentas.infrastructure.entity.CuentaEntity;
import com.banco.mscuentas.infrastructure.entity.MovimientoEntity;
import com.banco.mscuentas.infrastructure.repository.JpaCuentaRepository;
import com.banco.mscuentas.infrastructure.repository.JpaMovimientoRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReporteRepositoryAdapter implements ReporteService {
	private final JpaCuentaRepository cuentaRepository;
	private final JpaMovimientoRepository movimientoRepository;
	private final ClienteRepositoryAdapter clienteRepositoryAdapter;

	public ReporteRepositoryAdapter(JpaCuentaRepository cuentaRepository, JpaMovimientoRepository movimientoRepository,
									ClienteRepositoryAdapter clienteRepositoryAdapter) {
		this.cuentaRepository = cuentaRepository;
		this.movimientoRepository = movimientoRepository;
		this.clienteRepositoryAdapter = clienteRepositoryAdapter;
	}

	@Override
	public List<ReporteEstadoCuenta> generarReporte(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
		// 🔹 Validar que el cliente tenga cuentas
		List<CuentaEntity> cuentas = cuentaRepository.findByClienteId(clienteId);
		if (cuentas.isEmpty()) {
			throw new ClienteSinCuentasException(clienteId);
		}

		ClienteDTO cliente = clienteRepositoryAdapter.obtenerCliente(clienteId);


		// 🔹 Obtener movimientos en el rango de fechas
		LocalDateTime inicio = fechaInicio.atStartOfDay();
		LocalDateTime fin = fechaFin.plusDays(1).atStartOfDay(); // Incluye la fecha final completa

		return cuentas.stream().map(cuenta -> {
			List<MovimientoEntity> movimientos = movimientoRepository.findByCuentaIdAndFechaBetween(
					cuenta.getId(), inicio, fin);

			return new ReporteEstadoCuenta(
					clienteId,
					cliente.getNombre(),
					cliente.getIdentificacion(),
					cuenta.getNumeroCuenta(),
					cuenta.getSaldoInicial(),
					movimientos.stream().map(m -> new ReporteEstadoCuenta.MovimientoDTO(
							m.getFecha(), m.getTipoMovimiento(), m.getValor(), m.getSaldo()
					)).collect(Collectors.toList())
			);
		}).collect(Collectors.toList());
	}
}

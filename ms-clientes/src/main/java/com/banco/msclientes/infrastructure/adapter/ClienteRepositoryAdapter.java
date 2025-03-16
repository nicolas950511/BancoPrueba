package com.banco.msclientes.infrastructure.adapter;

import com.banco.msclientes.application.ClienteRepository;
import com.banco.msclientes.domain.Cliente;
import com.banco.msclientes.domain.exception.ClienteNotFoundException;
import com.banco.msclientes.domain.exception.BadRequestException;
import com.banco.msclientes.infrastructure.entity.ClienteEntity;
import com.banco.msclientes.infrastructure.repository.JpaClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClienteRepositoryAdapter implements ClienteRepository {
	private final JpaClienteRepository jpaClienteRepository;

	public ClienteRepositoryAdapter(JpaClienteRepository jpaClienteRepository) {
		this.jpaClienteRepository = jpaClienteRepository;
	}

	@Override
	public Cliente save(Cliente cliente) {
		if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
			throw new BadRequestException("El nombre del cliente es obligatorio.");
		}
		ClienteEntity entity = mapToEntity(cliente);
		ClienteEntity savedEntity = jpaClienteRepository.save(entity);
		return mapToDomain(savedEntity);
	}

	@Override
	public Optional<Cliente> findById(Long id) {
		return jpaClienteRepository.findById(id)
				.map(this::mapToDomain)
				.or(() -> {
					throw new ClienteNotFoundException(id);
				});
	}

	@Override
	public List<Cliente> findAll() {
		return jpaClienteRepository.findAll().stream()
				.map(this::mapToDomain)
				.collect(Collectors.toList());
	}

	@Override
	public Cliente update(Long id, Cliente cliente) {
		ClienteEntity existingEntity = jpaClienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException(id));

		if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
			throw new BadRequestException("El nombre del cliente es obligatorio para actualizar.");
		}

		existingEntity.setNombre(cliente.getNombre());
		existingEntity.setGenero(cliente.getGenero());
		existingEntity.setEdad(cliente.getEdad());
		existingEntity.setIdentificacion(cliente.getIdentificacion());
		existingEntity.setDireccion(cliente.getDireccion());
		existingEntity.setTelefono(cliente.getTelefono());
		existingEntity.setContraseña(cliente.getContraseña());
		existingEntity.setEstado(cliente.isEstado());

		ClienteEntity updatedEntity = jpaClienteRepository.save(existingEntity);
		return mapToDomain(updatedEntity);
	}

	@Override
	public Cliente patch(Long id, Cliente clienteParcial) {
		ClienteEntity existingEntity = jpaClienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException(id));

		if (clienteParcial.getNombre() != null) existingEntity.setNombre(clienteParcial.getNombre());
		if (clienteParcial.getGenero() != null) existingEntity.setGenero(clienteParcial.getGenero());
		if (clienteParcial.getEdad() != 0) existingEntity.setEdad(clienteParcial.getEdad());
		if (clienteParcial.getIdentificacion() != null) existingEntity.setIdentificacion(clienteParcial.getIdentificacion());
		if (clienteParcial.getDireccion() != null) existingEntity.setDireccion(clienteParcial.getDireccion());
		if (clienteParcial.getTelefono() != null) existingEntity.setTelefono(clienteParcial.getTelefono());
		if (clienteParcial.getContraseña() != null) existingEntity.setContraseña(clienteParcial.getContraseña());
		existingEntity.setEstado(clienteParcial.isEstado());

		ClienteEntity updatedEntity = jpaClienteRepository.save(existingEntity);
		return mapToDomain(updatedEntity);
	}

	@Override
	public void delete(Long id) {
		ClienteEntity existingEntity = jpaClienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException(id));
		jpaClienteRepository.delete(existingEntity);
	}

	private ClienteEntity mapToEntity(Cliente cliente) {
		ClienteEntity entity = new ClienteEntity();
		entity.setNombre(cliente.getNombre());
		entity.setGenero(cliente.getGenero());
		entity.setEdad(cliente.getEdad());
		entity.setIdentificacion(cliente.getIdentificacion());
		entity.setDireccion(cliente.getDireccion());
		entity.setTelefono(cliente.getTelefono());
		entity.setContraseña(cliente.getContraseña());
		entity.setEstado(cliente.isEstado());
		return entity;
	}

	private Cliente mapToDomain(ClienteEntity entity) {
		return new Cliente(
				entity.getNombre(),
				entity.getGenero(),
				entity.getEdad(),
				entity.getIdentificacion(),
				entity.getDireccion(),
				entity.getTelefono(),
				entity.getClienteId(),
				entity.getContraseña(),
				entity.isEstado()
		);
	}
}

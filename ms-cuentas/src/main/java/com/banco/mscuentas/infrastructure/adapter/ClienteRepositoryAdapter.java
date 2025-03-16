package com.banco.mscuentas.infrastructure.adapter;

import com.banco.mscuentas.domain.ClienteDTO;
import com.banco.mscuentas.domain.exception.ClienteNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class ClienteRepositoryAdapter {
	private final RestTemplate restTemplate;
	private final String clienteServiceUrl = "http://localhost:8081/clientes/";

	public ClienteRepositoryAdapter(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean clienteExiste(Long clienteId) {
		try {
			restTemplate.getForObject(clienteServiceUrl + clienteId, Object.class);
			return true;
		} catch (HttpClientErrorException.NotFound e) {
			return false;
		}
	}

	public ClienteDTO obtenerCliente(Long clienteId) {
		try {
			return restTemplate.getForObject(clienteServiceUrl + clienteId, ClienteDTO.class);
		} catch (HttpClientErrorException.NotFound e) {
			throw new ClienteNotFoundException(clienteId);
		}
	}
}

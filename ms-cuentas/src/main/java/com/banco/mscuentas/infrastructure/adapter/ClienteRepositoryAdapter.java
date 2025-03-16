package com.banco.mscuentas.infrastructure.adapter;

import com.banco.mscuentas.domain.ClienteDTO;
import com.banco.mscuentas.domain.exception.ClienteNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class ClienteRepositoryAdapter {
	private final RestTemplate restTemplate;
	private final String clienteServiceUrl;

	public ClienteRepositoryAdapter(RestTemplate restTemplate, 
	                                @Value("${cliente-service.url}") String clienteServiceUrl) {
		this.restTemplate = restTemplate;
		this.clienteServiceUrl = clienteServiceUrl;
		System.out.println("🚀 URL del servicio clientes: " + clienteServiceUrl);
	}

	public boolean clienteExiste(Long clienteId) {
		try {
			restTemplate.getForObject(clienteServiceUrl + "/" + clienteId, Object.class);
			return true;
		} catch (HttpClientErrorException.NotFound e) {
			return false;
		}
	}

	public ClienteDTO obtenerCliente(Long clienteId) {
		try {
			return restTemplate.getForObject(clienteServiceUrl + "/" + clienteId, ClienteDTO.class);
		} catch (HttpClientErrorException.NotFound e) {
			throw new ClienteNotFoundException(clienteId);
		}
	}
}

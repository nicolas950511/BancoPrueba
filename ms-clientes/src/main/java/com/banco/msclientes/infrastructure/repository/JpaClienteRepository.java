package com.banco.msclientes.infrastructure.repository;

import com.banco.msclientes.infrastructure.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClienteRepository extends JpaRepository<ClienteEntity, Long> {
}

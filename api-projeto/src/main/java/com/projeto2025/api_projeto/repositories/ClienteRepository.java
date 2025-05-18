package com.projeto2025.api_projeto.repositories;

import com.projeto2025.api_projeto.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCnpj(String cnpj);
    void deleteByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);
}


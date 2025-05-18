package com.projeto2025.api_projeto.repositories;

import com.projeto2025.api_projeto.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Métodos customizados podem ser adicionados se necessário
}


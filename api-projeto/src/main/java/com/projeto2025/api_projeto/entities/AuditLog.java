package com.projeto2025.api_projeto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String entidade; // Ex: "Cliente" ou "Usuario"

    @NotBlank
    private String acao; // Ex: "CREATE", "UPDATE", "DELETE"

    @NotBlank
    private String usuario; // login do usuário que fez a ação

    @NotNull
    private LocalDateTime dataHora;

    @Column(columnDefinition = "TEXT")
    private String detalhes; // JSON ou texto com detalhes da alteração
}


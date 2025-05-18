package com.projeto2025.api_projeto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @NotBlank
    private String loja;

    @NotNull
    private Integer codigoMunicipio;

    @NotBlank
    private String razaoSocial;

    @NotBlank
    private String nomeFantasia;

    @NotBlank
    private String ddd;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cidade;

    @NotBlank
    private String estado;

    @NotBlank
    private String bairro;

    @NotBlank
    private String pais;

    @NotBlank
    private String cep;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String homepage;

    @NotBlank
    private String tipoPessoa; // "FISICA" ou "JURIDICA"

    @NotBlank
    private String tipo; // Exemplo: "Cliente", "Fornecedor", etc.

    @NotBlank
    private String dataAberturaNascimento; // Formato: "yyyy-MM-dd"
}


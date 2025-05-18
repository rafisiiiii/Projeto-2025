package com.projeto2025.api_projeto.dto;

import lombok.Data;

@Data
public class DadosCnpjDTO {
    // Campos que a API pública retorna
    private String razaoSocial;
    private String nomeFantasia;
    private String dataAbertura;
    private String municipio;
    private String uf;
    private String cep;
    private String logradouro;
    private String numero;
    
}

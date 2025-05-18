package com.projeto2025.api_projeto.services;

import com.projeto2025.api_projeto.dto.DadosCnpjDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CNPJService {

    private final RestTemplate restTemplate; // Campo deve ser final

    public CNPJService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DadosCnpjDTO consultarCnpj(String cnpj) {
        String url = "https://brasilapi.com.br/api/cnpj/v1/" + cnpj;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> data = response.getBody();
            DadosCnpjDTO dto = new DadosCnpjDTO();
            
            dto.setRazaoSocial((String) data.get("razao_social"));
            dto.setNomeFantasia((String) data.get("nome_fantasia"));
            dto.setDataAbertura((String) data.get("data_inicio_atividade"));
            dto.setMunicipio((String) data.get("municipio"));
            dto.setUf((String) data.get("uf"));
            dto.setCep((String) data.get("cep"));
            dto.setLogradouro((String) data.get("logradouro"));
            dto.setNumero((String) data.get("numero"));
            
            return dto;
        }
        throw new RuntimeException("CNPJ não encontrado");
    }
}
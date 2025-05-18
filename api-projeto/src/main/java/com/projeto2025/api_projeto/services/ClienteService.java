package com.projeto2025.api_projeto.services;

import com.projeto2025.api_projeto.dto.ClienteDTO;
import com.projeto2025.api_projeto.dto.DadosCnpjDTO;
import com.projeto2025.api_projeto.entities.Cliente;
import com.projeto2025.api_projeto.entities.AuditLog;
import com.projeto2025.api_projeto.repositories.ClienteRepository;
import com.projeto2025.api_projeto.repositories.AuditLogRepository;
import com.projeto2025.api_projeto.services.CNPJService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
//import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final AuditLogRepository auditLogRepository;
    private final CNPJService cnpjService;
    private final EmailService emailService;

    @Transactional
    public Cliente salvarCliente(ClienteDTO dto) {
        if (clienteRepository.existsByCnpj(dto.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cliente já cadastrado com este CNPJ.");
        }

        DadosCnpjDTO cnpjData = cnpjService.consultarCnpj(dto.getCnpj());
        Cliente cliente = Cliente.builder()
                // ... (mantenha seu código de construção do cliente)
                .build();
        
        Cliente salvo = clienteRepository.save(cliente);

        // Auditoria
        auditLogRepository.save(AuditLog.builder()
                .entidade("Cliente")
                .acao("CREATE")
                .usuario("sistema")
                .dataHora(LocalDateTime.now())
                .detalhes("Cliente criado: " + salvo.getCnpj())
                .build());

        // E-mail automático 
        try {
            emailService.enviarConfirmacaoCadastroHtml(
                "setorfiscal@empresa.com", 
                salvo.getRazaoSocial(),
                salvo.getCnpj()
            );
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Falha ao enviar e-mail de confirmação: " + e.getMessage()
            );
        }

        return salvo;
    }

    public Cliente buscarPorCnpj(String cnpj) {
        return clienteRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    public Page<Cliente> listarTodos(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Transactional
    public Cliente atualizarCliente(String cnpj, ClienteDTO dto) {
        Cliente cliente = buscarPorCnpj(cnpj);
        cliente.setLoja(dto.getLoja());
        cliente.setCodigoMunicipio(dto.getCodigoMunicipio());
        cliente.setRazaoSocial(dto.getRazaoSocial());
        cliente.setNomeFantasia(dto.getNomeFantasia());
        cliente.setDdd(dto.getDdd());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());
        cliente.setBairro(dto.getBairro());
        cliente.setPais(dto.getPais());
        cliente.setCep(dto.getCep());
        cliente.setEmail(dto.getEmail());
        cliente.setHomepage(dto.getHomepage());
        cliente.setTipoPessoa(dto.getTipoPessoa());
        cliente.setTipo(dto.getTipo());
        cliente.setDataAberturaNascimento(dto.getDataAberturaNascimento());
        Cliente atualizado = clienteRepository.save(cliente);

        auditLogRepository.save(AuditLog.builder()
                .entidade("Cliente")
                .acao("UPDATE")
                .usuario("sistema")
                .dataHora(LocalDateTime.now())
                .detalhes("Cliente atualizado: " + atualizado.getCnpj())
                .build());

        return atualizado;
    }

    @Transactional
    public void excluirCliente(String cnpj) {
        Cliente cliente = buscarPorCnpj(cnpj);
        clienteRepository.delete(cliente);

        auditLogRepository.save(AuditLog.builder()
                .entidade("Cliente")
                .acao("DELETE")
                .usuario("sistema")
                .dataHora(LocalDateTime.now())
                .detalhes("Cliente excluído: " + cliente.getCnpj())
                .build());
    }
}


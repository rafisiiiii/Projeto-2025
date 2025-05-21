package com.projeto2025.api_projeto.services;

import com.projeto2025.api_projeto.dto.ClienteDTO;
import com.projeto2025.api_projeto.dto.DadosCnpjDTO;
import com.projeto2025.api_projeto.entities.Cliente;
import com.projeto2025.api_projeto.entities.AuditLog;
import com.projeto2025.api_projeto.repositories.ClienteRepository;
import com.projeto2025.api_projeto.repositories.AuditLogRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

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

        // Obtém o usuário autenticado
        String usuario = obterUsuarioAutenticado();

        // Consulta dados do CNPJ (apenas se for CNPJ válido)
        DadosCnpjDTO cnpjData = cnpjService.consultarCnpj(dto.getCnpj());

        // Constrói o cliente
        Cliente cliente = Cliente.builder()
                .cnpj(dto.getCnpj())
                .loja(dto.getLoja())
                .codigoMunicipio(dto.getCodigoMunicipio())
                .razaoSocial(dto.getRazaoSocial())
                .nomeFantasia(dto.getNomeFantasia())
                .ddd(dto.getDdd())
                .telefone(dto.getTelefone())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .bairro(dto.getBairro())
                .pais(dto.getPais())
                .cep(dto.getCep())
                .email(dto.getEmail())
                .homepage(dto.getHomepage())
                .tipoPessoa(dto.getTipoPessoa())
                .tipo(dto.getTipo())
                .dataAberturaNascimento(dto.getDataAberturaNascimento())
                .build();

        Cliente salvo = clienteRepository.save(cliente);

        // Registra auditoria com usuário real
        registrarAuditoria("CREATE", usuario, salvo.getCnpj());

        // Envia e-mail de confirmação
        try {
            emailService.enviarConfirmacaoCadastroHtml(
                "setorfiscal@empresa.com",
                salvo.getRazaoSocial(),
                salvo.getId().toString(), // Código do cliente
                salvo.getCnpj(),
                usuario
            );
        } catch (MessagingException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Falha ao enviar e-mail: " + e.getMessage()
            );
        }

        return salvo;
    }

    // Método auxiliar para obter usuário autenticado
    private String obterUsuarioAutenticado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal != null) {
            return principal.toString();
        }
        return "sistema"; // Default se não houver autenticação
    }

    // Método auxiliar para registrar auditoria
    private void registrarAuditoria(String acao, String usuario, String detalhes) {
        auditLogRepository.save(AuditLog.builder()
                .entidade("Cliente")
                .acao(acao)
                .usuario(usuario)
                .dataHora(LocalDateTime.now())
                .detalhes(detalhes)
                .build());
    }

    public Cliente buscarPorCnpj(String cnpj) {
        return clienteRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
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
        registrarAuditoria("UPDATE", obterUsuarioAutenticado(), "Atualização: " + atualizado.getCnpj());
        return atualizado;
    }

    @Transactional
    public void excluirCliente(String cnpj) {
        Cliente cliente = buscarPorCnpj(cnpj);
        clienteRepository.delete(cliente);
        registrarAuditoria("DELETE", obterUsuarioAutenticado(), "Exclusão: " + cliente.getCnpj());
    }
}

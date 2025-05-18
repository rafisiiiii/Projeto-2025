package com.projeto2025.api_projeto.controllers;


import com.projeto2025.api_projeto.dto.ClienteDTO;
import com.projeto2025.api_projeto.dto.DadosCnpjDTO;
import com.projeto2025.api_projeto.entities.Cliente;
import com.projeto2025.api_projeto.services.ClienteService;
import com.projeto2025.api_projeto.services.CNPJService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/clientes")
@SecurityRequirement(name = "bearer-key")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final CNPJService cnpjService;

    @PostMapping
    @Operation(summary = "Cria novo cliente com dados do CNPJ")
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody ClienteDTO dto) {
        return new ResponseEntity<>(clienteService.salvarCliente(dto), HttpStatus.CREATED);
    }

    @GetMapping("/buscar/{cnpj}")
    @Operation(summary = "Busca cliente por CNPJ")
    public ResponseEntity<Cliente> buscarPorCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(clienteService.buscarPorCnpj(cnpj));
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todos clientes paginados")
    public ResponseEntity<Page<Cliente>> listarClientes(Pageable pageable) {
        return ResponseEntity.ok(clienteService.listarTodos(pageable));
    }

    @PutMapping("/atualizar/{cnpj}")
    @Operation(summary = "Atualiza cliente existente")
    public ResponseEntity<Cliente> atualizarCliente(
            @PathVariable String cnpj, 
            @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.atualizarCliente(cnpj, dto));
    }

    @DeleteMapping("/excluir/{cnpj}")
    @Operation(summary = "Exclui cliente por CNPJ")
    public ResponseEntity<Void> excluirCliente(@PathVariable String cnpj) {
        clienteService.excluirCliente(cnpj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/consultar-cnpj")
    public ResponseEntity<DadosCnpjDTO> consultarCnpj(@RequestParam String cnpj) {
        return ResponseEntity.ok(cnpjService.consultarCnpj(cnpj));
    }

}



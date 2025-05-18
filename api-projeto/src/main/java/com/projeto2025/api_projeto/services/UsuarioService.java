package com.projeto2025.api_projeto.services;

import com.projeto2025.api_projeto.dto.UsuarioDTO;
import com.projeto2025.api_projeto.entities.Usuario;
import com.projeto2025.api_projeto.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByLogin(dto.getLogin())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Login já cadastrado.");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado.");
        }
        Usuario usuario = Usuario.builder()
                .login(dto.getLogin())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .role(dto.getRole())
                .build();
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}


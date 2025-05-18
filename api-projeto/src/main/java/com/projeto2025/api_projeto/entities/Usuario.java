package com.projeto2025.api_projeto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario implements UserDetails { // Implemente UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String senha;

    @NotBlank
    @Column(nullable = false)
    private String role; // Exemplo: "ADMIN" ou "USER"

    // Métodos obrigatórios do UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Altere conforme sua regra de negócio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Altere conforme sua regra de negócio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Altere conforme sua regra de negócio
    }

    @Override
    public boolean isEnabled() {
        return true; // Altere conforme sua regra de negócio
    }
}

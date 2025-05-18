package com.projeto2025.api_projeto.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    // Getters and Setters

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}


package com.projeto2025.api_projeto.dto;

import jakarta.validation.constraints.*;

public class ClienteDTO {

    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
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

    // Getters and Setters

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public Integer getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(Integer codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataAberturaNascimento() {
        return dataAberturaNascimento;
    }

    public void setDataAberturaNascimento(String dataAberturaNascimento) {
        this.dataAberturaNascimento = dataAberturaNascimento;
    }
}


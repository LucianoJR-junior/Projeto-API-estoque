package com.example.ProjetoEstoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriasRequestDto {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome não pode passar de 50 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 100, message = "A descrição não pode passar de 100 caracteres")
    private String descricao;

    public CategoriasRequestDto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
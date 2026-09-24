package com.example.ProjetoEstoque.dto;

import com.example.ProjetoEstoque.model.Categorias;

public class CategoriasResponseDto {

    private long id;
    private String nome;
    private String descricao;

    public CategoriasResponseDto() {
    }

    public CategoriasResponseDto(Categorias categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
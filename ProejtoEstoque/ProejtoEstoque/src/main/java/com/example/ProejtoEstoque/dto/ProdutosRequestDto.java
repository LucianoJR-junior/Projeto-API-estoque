package com.example.ProejtoEstoque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutosRequestDto {

    @NotBlank(message = "O nome é obrigatorio")
    @Size(max = 50, message = "O nome não pode passar de 50 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatoria")
    @Size(max = 100, message = "A descrição não pode passar de 100 caracteres")
    private String descricao;

    @NotNull(message = "o preco e obrigatorio")
    @Positive(message = "o preço deve ser maior que zero")
    private Double preco;

    @NotNull(message = "a quantidade é obrigatoria")
    @Min(value = 0, message = "a quantidade não pode ser negativa")
    private Integer quantidadeEstoque;

    @NotNull(message = "a categoria é obrigatoria")
    private Long categoriaId;

    public ProdutosRequestDto() {
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

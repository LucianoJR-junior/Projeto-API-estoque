package com.example.ProjetoEstoque.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ProjetoEstoque.dto.MovimentacaoEstoqueRequestDto;
import com.example.ProjetoEstoque.dto.ProdutosRequestDto;
import com.example.ProjetoEstoque.dto.ProdutosResponseDto;
import com.example.ProjetoEstoque.service.ProdutosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutosService produtosService;

    public ProdutosController(ProdutosService produtosService) {
        this.produtosService = produtosService;
    }

    // Criar Produto (POST /produtos -> 201 Created)
    @PostMapping
    public ResponseEntity<ProdutosResponseDto> criar(@Valid @RequestBody ProdutosRequestDto dto) {
        ProdutosResponseDto produto = produtosService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    // Listar Todos com filtros opcionais (GET /produtos?categoriaId=1&nome=mouse -> 200 OK)
    @GetMapping
    public ResponseEntity<List<ProdutosResponseDto>> listarTodos(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String nome) {
        return ResponseEntity.ok(produtosService.buscar(categoriaId, nome));
    }

    // Buscar por ID (GET /produtos/{id} -> 200 OK)
    @GetMapping("/{id}")
    public ResponseEntity<ProdutosResponseDto> buscarPorId(@PathVariable Long id) {
        ProdutosResponseDto produto = produtosService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    // Filtrar por Categoria (GET /produtos/categoria/{categoriaId} -> 200 OK)
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutosResponseDto>> buscarPorCategoria(@PathVariable Long categoriaId) {
        List<ProdutosResponseDto> produtos = produtosService.buscarPorCategoria(categoriaId);
        return ResponseEntity.ok(produtos);
    }

    // Atualizar Produto completo (PUT /produtos/{id} -> 200 OK)
    @PutMapping("/{id}")
    public ResponseEntity<ProdutosResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutosRequestDto dto) {
        ProdutosResponseDto produto = produtosService.atualizar(id, dto);
        return ResponseEntity.ok(produto);
    }

    // Movimentar Estoque (PATCH /produtos/{id}/estoque -> 200 OK)
    // Corpo: { "tipo": "ENTRADA" ou "SAIDA", "quantidade": 10 }
    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutosResponseDto> movimentarEstoque(
            @PathVariable Long id,
            @Valid @RequestBody MovimentacaoEstoqueRequestDto dto) {
        ProdutosResponseDto produto = produtosService.movimentarEstoque(id, dto);
        return ResponseEntity.ok(produto);
    }

    // Excluir Produto (DELETE /produtos/{id} -> 204 No Content)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

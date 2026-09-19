package com.example.ProejtoEstoque.controller;

import com.example.ProejtoEstoque.service.CategoriasService;
import org.springframework.web.bind.annotation.*;
import com.example.ProejtoEstoque.dto.CategoriasRequestDto;
import com.example.ProejtoEstoque.dto.CategoriasResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    private final CategoriasService categoriasService;

    public CategoriasController(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    @PostMapping
    public ResponseEntity<CategoriasResponseDto> criar(
            @Valid @RequestBody CategoriasRequestDto dto) {

        CategoriasResponseDto categoria = categoriasService.criar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriasResponseDto>> listarTodos() {

        List<CategoriasResponseDto> categorias = categoriasService.listarTodos();

        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriasResponseDto> buscarPorId(
            @PathVariable Long id) {

        CategoriasResponseDto categoria = categoriasService.buscarPorId(id);

        return ResponseEntity.ok(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriasResponseDto> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CategoriasRequestDto dto) {

        CategoriasResponseDto categoria = categoriasService.atualizar(id, dto);

        return ResponseEntity.ok(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        categoriasService.excluir(id);

        return ResponseEntity.noContent().build();
    }
}
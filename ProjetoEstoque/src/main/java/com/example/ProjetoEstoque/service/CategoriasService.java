package com.example.ProjetoEstoque.service;

import com.example.ProjetoEstoque.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import com.example.ProjetoEstoque.repository.CategoriasRepository;
import com.example.ProjetoEstoque.dto.CategoriasRequestDto;
import com.example.ProjetoEstoque.dto.CategoriasResponseDto;
import com.example.ProjetoEstoque.model.Categorias;

import java.util.List;

@Service
public class CategoriasService {

    private final CategoriasRepository categoriasRepository;

    public CategoriasService(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    public CategoriasResponseDto criar(CategoriasRequestDto dto) {

        Categorias categoria = new Categorias();

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categorias categoriaSalva = categoriasRepository.save(categoria);

        return new CategoriasResponseDto(categoriaSalva);
    }

    public List<CategoriasResponseDto> listarTodos() {

        List<Categorias> categorias = categoriasRepository.findAll();

        return categorias.stream()
                .map(CategoriasResponseDto::new)
                .toList();
    }

    public CategoriasResponseDto buscarPorId(Long id) {

        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada com o ID: " + id));

        return new CategoriasResponseDto(categoria);
    }

    public CategoriasResponseDto atualizar(Long id, CategoriasRequestDto dto) {

        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada com o ID: " + id));

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categorias categoriaAtualizada = categoriasRepository.save(categoria);

        return new CategoriasResponseDto(categoriaAtualizada);
    }

    public void excluir(Long id) {

        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada com o ID: " + id));

        categoriasRepository.delete(categoria);
    }
}

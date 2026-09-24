package com.example.ProjetoEstoque.service;

import com.example.ProjetoEstoque.exception.RegraNegocioException;
import com.example.ProjetoEstoque.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ProjetoEstoque.repository.CategoriasRepository;
import com.example.ProjetoEstoque.repository.ProdutosRepository;
import com.example.ProjetoEstoque.dto.CategoriasRequestDto;
import com.example.ProjetoEstoque.dto.CategoriasResponseDto;
import com.example.ProjetoEstoque.model.Categorias;

import java.util.List;

@Service
public class CategoriasService {

    private final CategoriasRepository categoriasRepository;
    private final ProdutosRepository produtosRepository;

    public CategoriasService(CategoriasRepository categoriasRepository, ProdutosRepository produtosRepository) {
        this.categoriasRepository = categoriasRepository;
        this.produtosRepository = produtosRepository;
    }

    @Transactional
    public CategoriasResponseDto criar(CategoriasRequestDto dto) {

        Categorias categoria = new Categorias();

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());

        Categorias categoriaSalva = categoriasRepository.save(categoria);

        return new CategoriasResponseDto(categoriaSalva);
    }

    @Transactional(readOnly = true)
    public List<CategoriasResponseDto> listarTodos() {

        List<Categorias> categorias = categoriasRepository.findAll();

        return categorias.stream()
                .map(CategoriasResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriasResponseDto buscarPorId(Long id) {

        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada com o ID: " + id));

        return new CategoriasResponseDto(categoria);
    }

    @Transactional
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

    @Transactional
    public void excluir(Long id) {

        Categorias categoria = categoriasRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Categoria não encontrada com o ID: " + id));

        // Não permite excluir categoria que ainda possui produtos vinculados
        if (produtosRepository.existsByCategoriaId(id)) {
            throw new RegraNegocioException(
                    "Não é possível excluir a categoria pois existem produtos vinculados a ela");
        }

        categoriasRepository.delete(categoria);
    }
}

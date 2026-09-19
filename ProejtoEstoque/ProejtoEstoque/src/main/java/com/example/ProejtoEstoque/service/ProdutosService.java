package com.example.ProejtoEstoque.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ProejtoEstoque.dto.ProdutosRequestDto;
import com.example.ProejtoEstoque.dto.ProdutosResponseDto;
import com.example.ProejtoEstoque.exception.ResourceNotFoundException;
import com.example.ProejtoEstoque.model.Categorias;
import com.example.ProejtoEstoque.model.Produtos;
import com.example.ProejtoEstoque.repository.CategoriasRepository;
import com.example.ProejtoEstoque.repository.ProdutosRepository;

@Service
public class ProdutosService {

    private final ProdutosRepository produtosRepository;
    private final CategoriasRepository categoriasRepository;

    // Injeção dos dois repositórios pelo construtor
    public ProdutosService(ProdutosRepository produtosRepository, CategoriasRepository categoriasRepository) {
        this.produtosRepository = produtosRepository;
        this.categoriasRepository = categoriasRepository;
    }

    // 1. Criar Produto
    public ProdutosResponseDto criar(ProdutosRequestDto dto) {
        // Valida se a categoria enviada existe no banco
        Categorias categoria = categoriasRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria não encontrada com o id: " + dto.getCategoriaId()));

        Produtos produto = new Produtos();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        produto.setCategoria(categoria);

        Produtos produtoSalvo = produtosRepository.save(produto);
        return new ProdutosResponseDto(produtoSalvo);
    }

    // 2. Listar Todos
    public List<ProdutosResponseDto> listarTodos() {
        return produtosRepository.findAll().stream()
                .map(ProdutosResponseDto::new)
                .toList();
    }

    // 3. Buscar por ID
    public ProdutosResponseDto buscarPorId(Long id) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        return new ProdutosResponseDto(produto);
    }

    // 4. Filtrar por Categoria (Requisito Tema 5)
    public List<ProdutosResponseDto> buscarPorCategoria(Long categoriaId) {
        // Verifica se a categoria existe
        if (!categoriasRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o id: " + categoriaId);
        }

        return produtosRepository.findByCategoriaId(categoriaId).stream()
                .map(ProdutosResponseDto::new)
                .toList();
    }

    // 5. Atualizar Produto completo
    public ProdutosResponseDto atualizar(Long id, ProdutosRequestDto dto) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        Categorias categoria = categoriasRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoria não encontrada com o id: " + dto.getCategoriaId()));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        produto.setCategoria(categoria);

        Produtos produtoAtualizado = produtosRepository.save(produto);
        return new ProdutosResponseDto(produtoAtualizado);
    }

    // 6. Atualizar apenas o Estoque (Requisito Tema 5)
    public ProdutosResponseDto atualizarEstoque(Long id, Integer novaQuantidade) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        produto.setQuantidadeEstoque(novaQuantidade);
        Produtos produtoAtualizado = produtosRepository.save(produto);

        return new ProdutosResponseDto(produtoAtualizado);
    }

    // 7. Excluir Produto
    public void excluir(Long id) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        produtosRepository.delete(produto);
    }
}

package com.example.ProjetoEstoque.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ProjetoEstoque.dto.MovimentacaoEstoqueRequestDto;
import com.example.ProjetoEstoque.dto.ProdutosRequestDto;
import com.example.ProjetoEstoque.dto.ProdutosResponseDto;
import com.example.ProjetoEstoque.exception.RegraNegocioException;
import com.example.ProjetoEstoque.exception.ResourceNotFoundException;
import com.example.ProjetoEstoque.model.Categorias;
import com.example.ProjetoEstoque.model.Produtos;
import com.example.ProjetoEstoque.model.TipoMovimentacao;
import com.example.ProjetoEstoque.repository.CategoriasRepository;
import com.example.ProjetoEstoque.repository.ProdutosRepository;

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
    @Transactional
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
    @Transactional(readOnly = true)
    public List<ProdutosResponseDto> listarTodos() {
        return produtosRepository.findAll().stream()
                .map(ProdutosResponseDto::new)
                .toList();
    }

    // Busca com filtros opcionais: por categoria e/ou por parte do nome
    @Transactional(readOnly = true)
    public List<ProdutosResponseDto> buscar(Long categoriaId, String nome) {
        boolean temNome = nome != null && !nome.isBlank();

        if (categoriaId == null && !temNome) {
            return listarTodos();
        }

        if (categoriaId == null) {
            return produtosRepository.findByNomeContainingIgnoreCase(nome.trim()).stream()
                    .map(ProdutosResponseDto::new)
                    .toList();
        }

        if (!temNome) {
            return buscarPorCategoria(categoriaId);
        }

        if (!categoriasRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoria não encontrada com o id: " + categoriaId);
        }

        return produtosRepository.findByCategoriaIdAndNomeContainingIgnoreCase(categoriaId, nome.trim()).stream()
                .map(ProdutosResponseDto::new)
                .toList();
    }

    // 3. Buscar por ID
    @Transactional(readOnly = true)
    public ProdutosResponseDto buscarPorId(Long id) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        return new ProdutosResponseDto(produto);
    }

    // 4. Filtrar por Categoria (Requisito Tema 5)
    @Transactional(readOnly = true)
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
    @Transactional
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

    // 6. Movimentar o Estoque: ENTRADA soma, SAIDA subtrai (Requisito Tema 5)
    @Transactional
    public ProdutosResponseDto movimentarEstoque(Long id, MovimentacaoEstoqueRequestDto dto) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        int estoqueAtual = produto.getQuantidadeEstoque();

        if (dto.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidadeEstoque(estoqueAtual + dto.getQuantidade());
        } else {
            // Não permite que o estoque fique negativo
            if (dto.getQuantidade() > estoqueAtual) {
                throw new RegraNegocioException("Estoque insuficiente. Disponível: " + estoqueAtual
                        + ", solicitado: " + dto.getQuantidade());
            }
            produto.setQuantidadeEstoque(estoqueAtual - dto.getQuantidade());
        }

        Produtos produtoAtualizado = produtosRepository.save(produto);

        return new ProdutosResponseDto(produtoAtualizado);
    }

    // 7. Excluir Produto
    @Transactional
    public void excluir(Long id) {
        Produtos produto = produtosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));

        produtosRepository.delete(produto);
    }
}

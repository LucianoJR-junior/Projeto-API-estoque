package com.example.ProjetoEstoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.ProjetoEstoque.model.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    // O Spring Data JPA gera automaticamente a consulta para buscar produtos pelo ID da categoria
    List<Produtos> findByCategoriaId(Long categoriaId);

}

package com.example.ProejtoEstoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.ProejtoEstoque.model.Produtos;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {

    // O spring gera uma query na criação do arquivo para buscar por categoria
    List<Produtos> findByCategoriaId(Long categoriaId);

}
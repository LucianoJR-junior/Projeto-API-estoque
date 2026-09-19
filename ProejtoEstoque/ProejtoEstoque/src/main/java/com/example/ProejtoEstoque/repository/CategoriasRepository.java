package com.example.ProejtoEstoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ProejtoEstoque.model.Categorias;

@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {

}

-- =====================================================================
-- Banco de dados da API de Catálogo de Produtos e Categorias com Estoque
-- Compatível com MySQL 8+ e MariaDB 10.4+ (XAMPP)
-- =====================================================================

CREATE DATABASE IF NOT EXISTS estoque_db
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;

USE estoque_db;

-- ---------------------------------------------------------------------
-- Tabela: categorias
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS categorias (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(50)  NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------------
-- Tabela: produtos (Many-to-One com categorias)
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS produtos (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    nome               VARCHAR(50)  NOT NULL,
    descricao          VARCHAR(100) NOT NULL,
    preco              DOUBLE       DEFAULT NULL,
    quantidade_estoque INT          DEFAULT NULL,
    categoria_id       BIGINT       DEFAULT NULL,
    PRIMARY KEY (id),
    KEY idx_produtos_categoria (categoria_id),
    CONSTRAINT fk_produtos_categoria
        FOREIGN KEY (categoria_id) REFERENCES categorias (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_general_ci;

-- ---------------------------------------------------------------------
-- Dados de exemplo (opcional: apague este bloco para começar vazio)
-- ---------------------------------------------------------------------
INSERT INTO categorias (nome, descricao) VALUES
    ('Informática', 'Periféricos, cabos e acessórios'),
    ('Eletrônicos', 'Aparelhos e dispositivos eletrônicos'),
    ('Escritório',  'Materiais e móveis para escritório');

INSERT INTO produtos (nome, descricao, preco, quantidade_estoque, categoria_id) VALUES
    ('Mouse Gamer',      'Mouse RGB 16000 DPI',        149.90, 25, 1),
    ('Teclado Mecânico', 'Switch azul, ABNT2',         299.00, 10, 1),
    ('Monitor 24"',      'Full HD, 75Hz',              899.90,  5, 2),
    ('Fone Bluetooth',   'Cancelamento de ruído',      349.50, 12, 2),
    ('Cadeira de Escritório', 'Ergonômica, com apoio lombar', 1199.00, 3, 3);

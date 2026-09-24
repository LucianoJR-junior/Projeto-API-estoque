# Testando a API no Postman

## Opção 1: importar a coleção pronta (recomendado)

1. Suba o MySQL (XAMPP) e rode a aplicação. Espere aparecer `Started ProjetoEstoqueApplication`.
2. No Postman, clique em **Import** (canto superior esquerdo).
3. Arraste o arquivo `postman/ProjetoEstoque.postman_collection.json` ou clique em **files** e selecione-o.
4. A coleção **API Catálogo de Produtos e Estoque** aparece na barra lateral, com 6 pastas.
5. Execute as requisições **em ordem** (1, 2, 3...). Os IDs criados são guardados sozinhos nas variáveis `{{categoriaId}}`, `{{produtoId}}` e `{{produtoId2}}`.
6. Para rodar tudo de uma vez: clique nos `...` da coleção, **Run collection** e depois **Run**. Cada requisição mostra se o status HTTP veio como esperado.

A URL base fica na variável `{{baseUrl}}` (`http://localhost:8080`). Para mudar, clique na coleção e abra a aba **Variables**.

## Opção 2: montar manualmente

Para cada requisição: clique em **New > HTTP**, escolha o **método**, cole a **URL** e, quando houver corpo, vá em **Body > raw**, selecione **JSON** e cole o JSON.

> **Atenção aos IDs:** os exemplos abaixo usam `1` e `2`, mas o MySQL nunca reaproveita IDs apagados,
> então no seu banco eles provavelmente serão outros. Se usar um ID que não existe, a API responde
> **404 "Categoria não encontrada"**. Sempre crie a categoria primeiro, copie o `"id"` que volta na
> resposta e use esse número no `categoriaId` dos produtos. Faça o mesmo com o `id` do produto nas URLs.
> Para ver os IDs existentes: `GET http://localhost:8080/categorias` e `GET http://localhost:8080/produtos`.

### 1 - Categorias (CRUD)

#### 1. Criar categoria

- **Método:** `POST`
- **URL:** `http://localhost:8080/categorias`
- **Status esperado:** `201`
- **Body (raw / JSON):**

```json
{
  "nome": "Informática",
  "descricao": "Periféricos e acessórios"
}
```

#### 2. Listar categorias

- **Método:** `GET`
- **URL:** `http://localhost:8080/categorias`
- **Status esperado:** `200`

#### 3. Buscar categoria por ID

- **Método:** `GET`
- **URL:** `http://localhost:8080/categorias/1`
- **Status esperado:** `200`

#### 4. Atualizar categoria

- **Método:** `PUT`
- **URL:** `http://localhost:8080/categorias/1`
- **Status esperado:** `200`
- **Body (raw / JSON):**

```json
{
  "nome": "Informática",
  "descricao": "Periféricos, cabos e acessórios"
}
```

### 2 - Produtos (CRUD)

#### 5. Criar produto (Mouse)

- **Método:** `POST`
- **URL:** `http://localhost:8080/produtos`
- **Status esperado:** `201`
- **Body (raw / JSON):**

```json
{
  "nome": "Mouse Gamer",
  "descricao": "Mouse RGB 16000 DPI",
  "preco": 149.90,
  "quantidadeEstoque": 10,
  "categoriaId": 1
}
```

#### 6. Criar produto (Teclado)

- **Método:** `POST`
- **URL:** `http://localhost:8080/produtos`
- **Status esperado:** `201`
- **Body (raw / JSON):**

```json
{
  "nome": "Teclado Mecânico",
  "descricao": "Switch azul, ABNT2",
  "preco": 299.00,
  "quantidadeEstoque": 5,
  "categoriaId": 1
}
```

#### 7. Listar produtos

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos`
- **Status esperado:** `200`

#### 8. Buscar produto por ID

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos/1`
- **Status esperado:** `200`

#### 9. Atualizar produto

- **Método:** `PUT`
- **URL:** `http://localhost:8080/produtos/1`
- **Status esperado:** `200`
- **Body (raw / JSON):**

```json
{
  "nome": "Mouse Gamer Pro",
  "descricao": "Mouse RGB 16000 DPI, sem fio",
  "preco": 199.90,
  "quantidadeEstoque": 10,
  "categoriaId": 1
}
```

### 3 - Busca e filtragem

#### 10. Filtrar por categoria (query param)

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos?categoriaId=1`
- **Status esperado:** `200`

#### 11. Buscar por nome

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos?nome=mouse`
- **Status esperado:** `200`

#### 12. Filtrar por categoria + nome

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos?categoriaId=1&nome=teclado`
- **Status esperado:** `200`

#### 13. Filtrar por categoria (rota)

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos/categoria/1`
- **Status esperado:** `200`

### 4 - Movimentação de estoque

#### 14. Entrada de estoque (+5)

- **Método:** `PATCH`
- **URL:** `http://localhost:8080/produtos/1/estoque`
- **Status esperado:** `200`
- **Body (raw / JSON):**

```json
{
  "tipo": "ENTRADA",
  "quantidade": 5
}
```

#### 15. Saída de estoque (-3)

- **Método:** `PATCH`
- **URL:** `http://localhost:8080/produtos/1/estoque`
- **Status esperado:** `200`
- **Body (raw / JSON):**

```json
{
  "tipo": "SAIDA",
  "quantidade": 3
}
```

### 5 - Validações e erros

#### 16. Categoria inválida (campos vazios)

- **Método:** `POST`
- **URL:** `http://localhost:8080/categorias`
- **Status esperado:** `400`
- **Body (raw / JSON):**

```json
{
  "nome": "",
  "descricao": ""
}
```

#### 17. Categoria inexistente

- **Método:** `GET`
- **URL:** `http://localhost:8080/categorias/99999`
- **Status esperado:** `404`

#### 18. Produto inválido (preço e estoque negativos)

- **Método:** `POST`
- **URL:** `http://localhost:8080/produtos`
- **Status esperado:** `400`
- **Body (raw / JSON):**

```json
{
  "nome": "",
  "descricao": "",
  "preco": -10,
  "quantidadeEstoque": -1
}
```

#### 19. Produto com categoria inexistente

- **Método:** `POST`
- **URL:** `http://localhost:8080/produtos`
- **Status esperado:** `404`
- **Body (raw / JSON):**

```json
{
  "nome": "Produto X",
  "descricao": "Teste",
  "preco": 10,
  "quantidadeEstoque": 1,
  "categoriaId": 99999
}
```

#### 20. Produto inexistente

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos/99999`
- **Status esperado:** `404`

#### 21. ID com formato inválido

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos/abc`
- **Status esperado:** `400`

#### 22. Saída maior que o estoque

- **Método:** `PATCH`
- **URL:** `http://localhost:8080/produtos/1/estoque`
- **Status esperado:** `400`
- **Body (raw / JSON):**

```json
{
  "tipo": "SAIDA",
  "quantidade": 1000
}
```

#### 23. Tipo de movimentação inválido

- **Método:** `PATCH`
- **URL:** `http://localhost:8080/produtos/1/estoque`
- **Status esperado:** `400`
- **Body (raw / JSON):**

```json
{
  "tipo": "XYZ",
  "quantidade": 1
}
```

#### 24. Quantidade zero na movimentação

- **Método:** `PATCH`
- **URL:** `http://localhost:8080/produtos/1/estoque`
- **Status esperado:** `400`
- **Body (raw / JSON):**

```json
{
  "tipo": "ENTRADA",
  "quantidade": 0
}
```

#### 25. JSON malformado

- **Método:** `POST`
- **URL:** `http://localhost:8080/categorias`
- **Status esperado:** `400`
- **Body (raw / JSON):**

```json
{ "nome": "Sem fechar
```

#### 26. Excluir categoria com produtos

- **Método:** `DELETE`
- **URL:** `http://localhost:8080/categorias/1`
- **Status esperado:** `400`

#### 27. Verbo não suportado

- **Método:** `PATCH`
- **URL:** `http://localhost:8080/categorias/1`
- **Status esperado:** `405`

### 6 - Exclusões

#### 28. Excluir produto (Mouse)

- **Método:** `DELETE`
- **URL:** `http://localhost:8080/produtos/1`
- **Status esperado:** `204`

#### 29. Excluir produto (Teclado)

- **Método:** `DELETE`
- **URL:** `http://localhost:8080/produtos/2`
- **Status esperado:** `204`

#### 30. Buscar produto excluído

- **Método:** `GET`
- **URL:** `http://localhost:8080/produtos/1`
- **Status esperado:** `404`

#### 31. Excluir categoria (agora vazia)

- **Método:** `DELETE`
- **URL:** `http://localhost:8080/categorias/1`
- **Status esperado:** `204`

## Referência rápida dos endpoints

| Método | URL | O que faz |
|---|---|---|
| POST | `/categorias` | Cria categoria |
| GET | `/categorias` | Lista categorias |
| GET | `/categorias/{id}` | Busca categoria |
| PUT | `/categorias/{id}` | Atualiza categoria |
| DELETE | `/categorias/{id}` | Exclui categoria (só se não tiver produtos) |
| POST | `/produtos` | Cria produto |
| GET | `/produtos` | Lista produtos |
| GET | `/produtos?categoriaId={id}&nome={texto}` | Filtra por categoria e/ou nome (ambos opcionais) |
| GET | `/produtos/categoria/{id}` | Lista produtos de uma categoria |
| GET | `/produtos/{id}` | Busca produto |
| PUT | `/produtos/{id}` | Atualiza produto |
| PATCH | `/produtos/{id}/estoque` | Movimenta estoque: `{"tipo": "ENTRADA" ou "SAIDA", "quantidade": n}` |
| DELETE | `/produtos/{id}` | Exclui produto |

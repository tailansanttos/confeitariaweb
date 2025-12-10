CREATE TABLE tb_categorias(
     id BIGSERIAL PRIMARY KEY,
     nome VARCHAR(50)
);

CREATE TABLE tb_produtos
(
    id           BIGSERIAL PRIMARY KEY,
    nome         VARCHAR(100) NOT NULL,
    descricao    VARCHAR(200) NOT NULL,
    preco        NUMERIC      NOT NULL,
    img_url      VARCHAR(155) NOT NULL,
    ativo        BOOLEAN,
    categoria_id BIGINT       NOT NULL,

    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id) REFERENCES tb_categorias (id)
);

CREATE TABLE tb_usuarios(
       id BIGSERIAL PRIMARY KEY,
       nome VARCHAR(177) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
     telefone VARCHAR(156) NOT NULL,
      password VARCHAR(255) NOT NULL,
     cpf VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE tb_enderecos(
      id BIGSERIAL PRIMARY KEY,
      cep VARCHAR(20) NOT NULL,
      logradouro VARCHAR(40) NOT NULL,
       complemento VARCHAR(30) NOT NULL,
      bairro VARCHAR(30) NOT NULL,
       cidade VARCHAR(30) NOT NULL,
     estado VARCHAR(35) NOT NULL,
     numero VARCHAR(15) NOT NULL,
     cliente_id BIGINT NOT NULL,

      CONSTRAINT fk_endereco_cliente
         FOREIGN KEY (cliente_id) REFERENCES tb_usuarios(id)
);

CREATE TABLE tb_pedidos(
    id BIGSERIAL PRIMARY KEY,
     momento_pedido TIMESTAMP NOT NULL,
   cliente_id BIGINT NOT NULL,

    CONSTRAINT fk_pedido_cliente
     FOREIGN KEY (cliente_id) REFERENCES tb_usuarios(id)
);

CREATE TABLE tb_pagamentos(
        id BIGINT PRIMARY KEY,
        status_pagamento INT NOT NULL,
        instante_pagamento TIMESTAMP,

        CONSTRAINT fk_pagamento_pedido
      FOREIGN KEY (id) REFERENCES tb_pedidos (id)
);

CREATE TABLE tb_item_pedidos(
      pedido_id  BIGINT NOT NULL,
      produto_id BIGINT NOT NULL,
      quantidade INT NOT NULL,
      preco      NUMERIC NOT NULL,

      PRIMARY KEY (pedido_id, produto_id),

       CONSTRAINT fk_itempedido_pedido
       FOREIGN KEY (pedido_id) REFERENCES tb_pedidos(id),

       CONSTRAINT fk_itempedido_produto
           FOREIGN KEY (produto_id) REFERENCES tb_produtos(id)
);

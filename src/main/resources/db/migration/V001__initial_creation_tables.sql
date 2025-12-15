CREATE TABLE tb_categories(
     id BIGSERIAL PRIMARY KEY,
     name VARCHAR(50)
);

CREATE TABLE tb_products (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    description    VARCHAR(200) NOT NULL,
    price        NUMERIC      NOT NULL,
    img_url      VARCHAR(155) NOT NULL,
    active        BOOLEAN,
    category_id BIGINT       NOT NULL,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id) REFERENCES tb_categories (id)
);

CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(177) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(156) NOT NULL,
    password VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE tb_address(
    id BIGSERIAL PRIMARY KEY,
    zip_code VARCHAR(20) NOT NULL,
    street VARCHAR(40) NOT NULL,
    complement VARCHAR(30) NOT NULL,
    neighborhood VARCHAR(30) NOT NULL,
    city VARCHAR(30) NOT NULL,
    state VARCHAR(35) NOT NULL,
    number VARCHAR(15) NOT NULL,
    client_id BIGINT NOT NULL,

      CONSTRAINT fk_address_client
         FOREIGN KEY (client_id) REFERENCES tb_users(id)
);

CREATE TABLE tb_orders(
    id BIGSERIAL PRIMARY KEY,
    moment_order TIMESTAMP NOT NULL,
    client_id BIGINT NOT NULL,

    CONSTRAINT fk_order_client
     FOREIGN KEY (client_id) REFERENCES tb_users(id)
);

CREATE TABLE tb_payments(
        id BIGINT PRIMARY KEY,
        status_payment INT NOT NULL,
        instant_payment TIMESTAMP,

        CONSTRAINT fk_payment_order
      FOREIGN KEY (id) REFERENCES tb_orders (id)
);

CREATE TABLE tb_item_orders(
      order_id  BIGINT NOT NULL,
      product_id BIGINT NOT NULL,
      quantity INT NOT NULL,
      price      NUMERIC NOT NULL,

      PRIMARY KEY (order_id, product_id),

       CONSTRAINT fk_itemorder_order
       FOREIGN KEY (order_id) REFERENCES tb_orders(id),

       CONSTRAINT fk_itemorder_product
           FOREIGN KEY (product_id) REFERENCES tb_products(id)
);

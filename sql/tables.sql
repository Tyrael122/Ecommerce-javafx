CREATE TABLE IF NOT EXISTS cliente
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf       VARCHAR(11) UNIQUE NOT NULL,
    nome      VARCHAR(255)       NOT NULL,
    email     VARCHAR(255),
    telefone  VARCHAR(20),
    endereco  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS funcionario
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    cpf       VARCHAR(11)  NOT NULL UNIQUE,
    senha     VARCHAR(255) NOT NULL,
    cargo     VARCHAR(50)  NOT NULL,
    nome      VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    endereco  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS produto
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(255)   NOT NULL,
    descricao  TEXT,
    preco      DECIMAL(10, 2) NOT NULL,
    quantidade INT            NOT NULL
);

CREATE TABLE IF NOT EXISTS venda
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    data           TIMESTAMP,
    cliente_id     BIGINT,
    funcionario_id BIGINT,
    valor_total    DOUBLE,
    finalizada     BOOLEAN,
    FOREIGN KEY (cliente_id) REFERENCES cliente (id),
    FOREIGN KEY (funcionario_id) REFERENCES funcionario (id)
);

CREATE TABLE IF NOT EXISTS venda_itens
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    venda_id   BIGINT,
    produto_id BIGINT,
    quantidade INT,
    FOREIGN KEY (venda_id) REFERENCES venda (id),
    FOREIGN KEY (produto_id) REFERENCES produto (id)
);
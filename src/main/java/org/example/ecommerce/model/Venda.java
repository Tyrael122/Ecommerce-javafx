package org.example.ecommerce.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Venda {
    private long id;
    private Carrinho carrinho;

    private LocalDateTime data = LocalDateTime.now();

    private Cliente cliente;
    private Funcionario funcionario;

    private double valorTotal;

    private boolean finalizada;
}

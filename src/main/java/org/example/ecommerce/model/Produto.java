package org.example.ecommerce.model;

import lombok.Data;

@Data
public class Produto {
    private long id;
    private String nome;
    private String descricao;

    private double preco;
    private int quantidadeEstoque;

    public Produto() {
    }

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco + " - " + quantidadeEstoque + " unid. em estoque";
    }
}

package org.example.ecommerce.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Carrinho {
    private final List<Produto> produtos = new ArrayList<>();

    public double calcularTotal() {
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }

    public Map<Produto, Integer> calcularQuantidadePorProduto() {
        Map<Produto, Integer> quantidadePorProduto = new HashMap<>();
        for (Produto produto : produtos) {
            quantidadePorProduto.put(produto, quantidadePorProduto.getOrDefault(produto, 0) + 1);
        }
        return quantidadePorProduto;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        int quantidadeJaExistente = calcularQuantidadePorProduto().getOrDefault(produto, 0);
        int quantidadeSolicitadaTotal = quantidade + quantidadeJaExistente;

        if (produto.getQuantidadeEstoque() < quantidadeSolicitadaTotal) {
            throw new IllegalArgumentException("Disponível em estoque: " + (produto.getQuantidadeEstoque() - quantidadeJaExistente));
        }

        for (int i = 0; i < quantidade; i++) {
            produtos.add(produto);
        }
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void removerProduto(Produto produto) {
        produtos.remove(produto);
    }

    public void limpar() {
        produtos.clear();
    }
}
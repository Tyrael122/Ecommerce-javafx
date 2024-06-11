package org.example.ecommerce.controller;

import org.example.ecommerce.dal.ClienteDAL;
import org.example.ecommerce.dal.ProdutoDAL;
import org.example.ecommerce.dal.VendaDAL;
import org.example.ecommerce.model.*;
import org.example.ecommerce.pix.DadosEnvioPix;
import org.example.ecommerce.pix.QRCodePix;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Map;

public class VendaController {
    private final Venda venda = new Venda();

    public void vincularCliente(String cpf) {
        ClienteDAL clienteDAL = new ClienteDAL();

        Cliente cliente = clienteDAL.buscarPorCpf(cpf);

        venda.setCliente(cliente);
    }

    public void finalizarVenda(Carrinho carrinho, Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }

        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho não pode ser nulo");
        }

        if (carrinho.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("Carrinho não pode estar vazio");
        }

        venda.setCarrinho(carrinho);
        venda.setValorTotal(carrinho.calcularTotal());

        venda.setFuncionario(funcionario);

        venda.setFinalizada(true);

        VendaDAL vendaDAL = new VendaDAL();
        vendaDAL.insert(venda);

        removerEstoqueProdutos(carrinho);
    }

    public Path obterCaminhoQrCodePix() {
        if (!venda.isFinalizada()) {
            throw new IllegalStateException("Venda não finalizada");
        }

        final var dadosPix = new DadosEnvioPix("Trabalhadores 6x1", "cpf de alguem aqui", BigDecimal.valueOf(venda.getValorTotal()), "Logo ali", "Pagamento de produtos comprados em ecommerce");

        Path imagePath = Path.of("qrcode.png");
        new QRCodePix(dadosPix).save(imagePath);

        return imagePath;
    }

    private void removerEstoqueProdutos(Carrinho carrinho) {
        ProdutoDAL produtoDAL = new ProdutoDAL();

        for (Map.Entry<Produto, Integer> produtoQuantidade : carrinho.calcularQuantidadePorProduto().entrySet()) {
            Produto produto = produtoQuantidade.getKey();
            Integer quantidadeNoCarrinho = produtoQuantidade.getValue();

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeNoCarrinho);
            produtoDAL.atualizar(produto);
        }
    }
}

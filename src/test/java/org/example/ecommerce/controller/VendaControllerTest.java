package org.example.ecommerce.controller;

import org.example.ecommerce.TestUtils;
import org.example.ecommerce.dal.ClienteDAL;
import org.example.ecommerce.dal.FuncionarioDAL;
import org.example.ecommerce.dal.ProdutoDAL;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Carrinho;
import org.example.ecommerce.model.Cliente;
import org.example.ecommerce.model.Funcionario;
import org.example.ecommerce.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.example.ecommerce.TestUtils.criarFuncionarioPadraoComCpf;
import static org.junit.jupiter.api.Assertions.*;

class VendaControllerTest {

    @BeforeEach
    void setUp() {
        try {
            Database.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        try {
            Database.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testVendaCompletaSemCliente() {
        VendaController vendaController = new VendaController();
        Carrinho carrinho = new Carrinho();

        Funcionario funcionario = inserirFuncionarioNoBanco();

        carrinho.adicionarProduto(inserirProdutoNoBanco("Produto 1"));
        carrinho.adicionarProduto(inserirProdutoNoBanco("Produto 2"));

        vendaController.finalizarVenda(carrinho, funcionario);

        assertDoesNotThrow(vendaController::obterCaminhoQrCodePix);
    }

    @Test
    void testVendaCompletaComCliente() {
        VendaController vendaController = new VendaController();
        Carrinho carrinho = new Carrinho();

        Funcionario funcionario = inserirFuncionarioNoBanco();

        carrinho.adicionarProduto(inserirProdutoNoBanco("Produto 1"));
        carrinho.adicionarProduto(inserirProdutoNoBanco("Produto 2"));

        String cpf = "12345678901";
        inserirClienteNoBanco(cpf);

        vendaController.vincularCliente(cpf);

        vendaController.finalizarVenda(carrinho, funcionario);

        assertDoesNotThrow(vendaController::obterCaminhoQrCodePix);
    }

    private static Produto inserirProdutoNoBanco(String nomeProduto) {
        Produto produto = TestUtils.criarProdutoPadraoComNome(nomeProduto);

        ProdutoDAL produtoDAL = new ProdutoDAL();

        return produtoDAL.inserir(produto);
    }

    private static Funcionario inserirFuncionarioNoBanco() {
        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();

        Funcionario funcionario = criarFuncionarioPadraoComCpf("12345678901");

        return funcionarioDAL.inserir(funcionario);
    }

    private void inserirClienteNoBanco(String cpf) {
        Cliente cliente = TestUtils.criarClientePadrao(cpf);

        ClienteDAL clienteDAL = new ClienteDAL();
        clienteDAL.inserir(cliente);
    }
}
package org.example.ecommerce.dal;

import org.example.ecommerce.TestUtils;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Produto;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDALTest {

    private final String nomePadrao = "Produto Teste";

    @BeforeAll
    static void setAsTest() {
        Database.isTest = true;
    }

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
    void inserir() {
        Produto produto = criarProdutoPadrao();

        ProdutoDAL produtoDAL = new ProdutoDAL();
        produto = produtoDAL.inserir(produto);

        assertNotNull(produto);
    }

    @Test
    void atualizar() {
        Produto produto = criarProdutoPadrao();

        ProdutoDAL produtoDAL = new ProdutoDAL();
        produto = produtoDAL.inserir(produto);

        assertNotNull(produto);

        produto = produtoDAL.buscarPorNome(nomePadrao).getFirst();
        produto.setPreco(15.99);

        boolean resultado = produtoDAL.atualizar(produto);
        assertTrue(resultado);
    }

    @Test
    void deletar() {
        Produto produto = criarProdutoPadrao();

        ProdutoDAL produtoDAL = new ProdutoDAL();
        produto = produtoDAL.inserir(produto);

        assertNotNull(produto);

        produto = produtoDAL.buscarPorNome(nomePadrao).getFirst();
        boolean resultado = produtoDAL.deletar(produto.getId());

        assertTrue(resultado);

        int tamanho = produtoDAL.buscarPorNome(nomePadrao).size();
        assertEquals(0, tamanho);
    }

    @Test
    void buscar() {
        Produto produto = criarProdutoPadrao();

        ProdutoDAL produtoDAL = new ProdutoDAL();
        produto = produtoDAL.inserir(produto);

        assertNotNull(produto);

        produto = produtoDAL.buscarPorNome(nomePadrao).getFirst();
        assertNotNull(produto);
    }

    @Test
    void buscarColecao() {
        Produto produto1 = criarProdutoPadrao();
        Produto produto2 = criarProdutoPadrao();
        produto2.setNome("Outro Produto");

        ProdutoDAL produtoDAL = new ProdutoDAL();
        produto1 = produtoDAL.inserir(produto1);

        assertNotNull(produto1);

        produto2 = produtoDAL.inserir(produto2);
        assertNotNull(produto2);

        List<Produto> produtos = produtoDAL.buscarPorNome("Produto");
        assertEquals(2, produtos.size());
    }

    private Produto criarProdutoPadrao() {
        return TestUtils.criarProdutoPadraoComNome(nomePadrao);
    }
}

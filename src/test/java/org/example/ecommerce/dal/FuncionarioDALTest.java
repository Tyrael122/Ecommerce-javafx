package org.example.ecommerce.dal;

import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Funcionario;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.example.ecommerce.TestUtils.criarFuncionarioPadraoComCpf;
import static org.junit.jupiter.api.Assertions.*;

class FuncionarioDALTest {

    private final String cpfPadrao = "12345678901";

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
        Funcionario funcionario = criarFuncionarioPadrao();

        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();
        funcionario = funcionarioDAL.inserir(funcionario);

        assertNotNull(funcionario);
    }

    @Test
    void atualizar() {
        Funcionario funcionario = criarFuncionarioPadrao();

        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();
        funcionario = funcionarioDAL.inserir(funcionario);

        assertNotNull(funcionario);

        funcionario = funcionarioDAL.buscarPorCpf(cpfPadrao);
        funcionario.setNome("Ciclano");

        boolean resultado = funcionarioDAL.atualizar(funcionario);
        assertTrue(resultado);
    }

    @Test
    void deletar() {
        Funcionario funcionario = criarFuncionarioPadrao();

        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();
        funcionario = funcionarioDAL.inserir(funcionario);

        assertNotNull(funcionario);

        funcionario = funcionarioDAL.buscarPorCpf(cpfPadrao);
        boolean resultado = funcionarioDAL.deletar(funcionario.getId());

        assertTrue(resultado);

        funcionario = funcionarioDAL.buscarPorCpf(cpfPadrao);
        assertNull(funcionario);
    }

    @Test
    void buscar() {
        Funcionario funcionario = criarFuncionarioPadrao();

        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();
        funcionario = funcionarioDAL.inserir(funcionario);

        assertNotNull(funcionario);

        funcionario = funcionarioDAL.buscarPorCpf(cpfPadrao);
        assertNotNull(funcionario);
    }

    @Test
    void buscarColecao() {
        Funcionario funcionario = criarFuncionarioPadrao();

        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();
        funcionario = funcionarioDAL.inserir(funcionario);

        assertNotNull(funcionario);

        funcionario = criarFuncionarioPadrao();
        funcionario.setCpf("98765432109");
        funcionario.setNome("Maria");

        funcionario = funcionarioDAL.inserir(funcionario);

        assertNotNull(funcionario);

        List<Funcionario> funcionarios = funcionarioDAL.buscar("nome LIKE '%a%'");
        assertEquals(2, funcionarios.size());
    }

    private Funcionario criarFuncionarioPadrao() {
        return criarFuncionarioPadraoComCpf(cpfPadrao);
    }
}
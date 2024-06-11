package org.example.ecommerce.controller;

import org.example.ecommerce.TestUtils;
import org.example.ecommerce.dal.FuncionarioDAL;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Funcionario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {
    private final LoginController loginController = new LoginController();
    private final FuncionarioDAL funcionarioDAL = new FuncionarioDAL();

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
    void loginCorreto() {
        String cpf = "12345678900";
        String senha = "senha123";

        Funcionario funcionario = TestUtils.criarFuncionarioPadraoComCpf(cpf);
        funcionario.setSenha(senha);

        funcionarioDAL.inserir(funcionario);

        Funcionario result = loginController.login(cpf, senha);

        assertEquals(funcionario, result);
    }

    @Test
    void loginIncorreto() {
        String cpf = "12345678900";
        String senha = "senha123";
        String senhaIncorreta = "senha456";

        Funcionario funcionario = TestUtils.criarFuncionarioPadraoComCpf(cpf);
        funcionario.setSenha(senha);

        funcionarioDAL.inserir(funcionario);

        assertThrows(IllegalArgumentException.class, () -> loginController.login(cpf, senhaIncorreta));
    }
}
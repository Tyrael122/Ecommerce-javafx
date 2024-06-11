package org.example.ecommerce.controller;

import org.example.ecommerce.dal.FuncionarioDAL;
import org.example.ecommerce.model.Funcionario;

public class LoginController {
    public Funcionario login(String cpf, String senha) {
        Funcionario funcionario = new FuncionarioDAL().buscarPorCpf(cpf);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não encontrado para o CPF " + cpf);
        }

        if (funcionario.getSenha().equals(senha)) {
            return funcionario;
        } else {
            throw new IllegalArgumentException("Senha incorreta");
        }
    }
}

package org.example.ecommerce;

import org.example.ecommerce.model.Cargo;
import org.example.ecommerce.model.Cliente;
import org.example.ecommerce.model.Funcionario;
import org.example.ecommerce.model.Produto;

public class TestUtils {
    public static Funcionario criarFuncionarioPadraoComCpf(String cpf) {
        Funcionario funcionario = new Funcionario();

        funcionario.setCpf(cpf);
        funcionario.setSenha("senha");
        funcionario.setCargo(Cargo.ADMINISTRADOR);
        funcionario.setNome("Fulano");
        funcionario.setEmail("exemplo@gmail.com");
        funcionario.setEndereco("Rua dos Bobos, nº 0");

        return funcionario;
    }

    public static Produto criarProdutoPadraoComNome(String nome) {
        Produto produto = new Produto();

        produto.setNome(nome);
        produto.setDescricao("Descrição do produto teste.");
        produto.setPreco(19.99);
        produto.setQuantidadeEstoque(10);

        return produto;
    }

    public static Cliente criarClientePadrao(String cpf) {
        Cliente cliente = new Cliente();

        cliente.setCpf(cpf);
        cliente.setNome("Cliente");
        cliente.setEmail("exemplo@gmail.com");
        cliente.setTelefone("123456789");
        cliente.setEndereco("Rua dos Bobos, nº 1");

        return cliente;
    }
}

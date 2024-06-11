package org.example.ecommerce.database;

import org.example.ecommerce.dal.ClienteDAL;
import org.example.ecommerce.dal.FuncionarioDAL;
import org.example.ecommerce.dal.ProdutoDAL;
import org.example.ecommerce.model.Cargo;
import org.example.ecommerce.model.Cliente;
import org.example.ecommerce.model.Funcionario;
import org.example.ecommerce.model.Produto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtils {

    public static void createDatabaseIfNotExists(String databaseName, Connection connection) throws SQLException {
        connection.prepareStatement("CREATE DATABASE IF NOT EXISTS " + databaseName).execute();
        connection.prepareStatement("USE " + databaseName).execute();
    }

    public static void createTablesIfNotExists(Connection connection) {
        File sqlFile = new File("sql/tables.sql");
        String[] sqlTables = readFile(sqlFile).split(";");

        for (String tableSql : sqlTables) {
            createTableIfNotExists(tableSql.trim(), connection);
        }
    }

    private static void createTableIfNotExists(String tableSql, Connection connection) {
        try {
            connection.prepareStatement(tableSql).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String readFile(File sqlFile) {
        try {
            return new String(Files.readAllBytes(sqlFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void createSampleDataIfNotExists() {
        createAdminFuncionarioIfNotExists();
        createSampleClienteIfNotExists();
        createSampleProductIfNotExists();
    }

    private static void createAdminFuncionarioIfNotExists() {
        FuncionarioDAL funcionarioDAL = new FuncionarioDAL();

        if (funcionarioDAL.buscarPorCpf("12345678900") != null) {
            return;
        }

        funcionarioDAL.inserir(getFuncionarioAdmin());
    }

    private static void createSampleClienteIfNotExists() {
        ClienteDAL clienteDAL = new ClienteDAL();
        if (clienteDAL.buscarPorCpf("12345678901") != null) {
            return;
        }

        clienteDAL.inserir(getClienteSample());
    }

    private static void createSampleProductIfNotExists() {
        ProdutoDAL produtoDAL = new ProdutoDAL();
        if (!produtoDAL.buscar().isEmpty()) {
            return;
        }

        Produto produto = new Produto();

        produto.setNome("Produto Exemplo");
        produto.setDescricao("Descrição do produto exemplo");
        produto.setPreco(100.0);
        produto.setQuantidadeEstoque(10);

        produtoDAL.inserir(produto);
    }

    private static Funcionario getFuncionarioAdmin() {
        Funcionario admin = new Funcionario();

        admin.setCpf("12345678900");
        admin.setSenha("admin");
        admin.setCargo(Cargo.ADMINISTRADOR);
        admin.setNome("Admin");
        admin.setEmail("example@gmail.com");
        admin.setEndereco("Rua dos Bobos, nº 0");

        return admin;
    }

    private static Cliente getClienteSample() {
        Cliente cliente = new Cliente();

        cliente.setCpf("12345678901");
        cliente.setNome("Cliente");
        cliente.setEmail("example@gmail.com");
        cliente.setTelefone("123456789");
        cliente.setEndereco("Rua dos Bobos, nº 1");

        return cliente;
    }
}
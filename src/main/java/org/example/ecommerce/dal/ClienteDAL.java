package org.example.ecommerce.dal;

import org.example.ecommerce.dal.utils.DALUtils;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAL {
    private final String NOME_TABELA = "cliente";

    public Cliente inserir(Cliente cliente) {
        String sql = "INSERT INTO " + NOME_TABELA + " (cpf, nome, email, telefone, endereco) VALUES (?, ?, ?, ?, ?)";

        return DALUtils.executarSql(sql, preparedStatement -> {
            inserirDadosNoSql(cliente, preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                cliente.setId(resultSet.getLong(1));
            }

            return cliente;
        });
    }

    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE " + NOME_TABELA +  " SET cpf = ?, nome = ?, email = ?, telefone = ?, endereco = ? WHERE id = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            inserirDadosNoSql(cliente, preparedStatement);
            preparedStatement.setLong(6, cliente.getId());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(long id) {
        return DALUtils.deletar(NOME_TABELA, id);
    }

    public Cliente buscarPorCpf(String cpf) {
        return DALUtils.pegarPrimeiroDaListaOuNulo(buscar("cpf LIKE '" + cpf + "'"));
    }

    public List<Cliente> buscar() {
        return buscar("1 = 1");
    }

    public List<Cliente> buscar(String criterio) {
        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE " + criterio;
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();

            List<Cliente> clientes = new ArrayList<>();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                clientes.add(converterResultSetParaCliente(resultSet));
            }

            return clientes;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void inserirDadosNoSql(Cliente cliente, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, cliente.getCpf());
        preparedStatement.setString(2, cliente.getNome());
        preparedStatement.setString(3, cliente.getEmail());
        preparedStatement.setString(4, cliente.getTelefone());
        preparedStatement.setString(5, cliente.getEndereco());
    }

    private Cliente converterResultSetParaCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(resultSet.getLong("id"));
        cliente.setCpf(resultSet.getString("cpf"));
        cliente.setNome(resultSet.getString("nome"));
        cliente.setEmail(resultSet.getString("email"));
        cliente.setTelefone(resultSet.getString("telefone"));
        cliente.setEndereco(resultSet.getString("endereco"));

        return cliente;
    }
}

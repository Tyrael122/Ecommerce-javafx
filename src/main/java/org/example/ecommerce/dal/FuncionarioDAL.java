package org.example.ecommerce.dal;

import org.example.ecommerce.dal.utils.DALUtils;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Cargo;
import org.example.ecommerce.model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAL {
    private final String NOME_TABELA = "funcionario";

    public Funcionario inserir(Funcionario funcionario) {
        String sql = "INSERT INTO " + NOME_TABELA + " (cpf, senha, cargo, nome, email, endereco) VALUES (?, ?, ?, ?, ?, ?)";

        return DALUtils.executarSql(sql, preparedStatement -> {
            inserirDadosNoSql(funcionario, preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                funcionario.setId(resultSet.getLong(1));
            }

            return funcionario;
        });
    }

    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE " + NOME_TABELA + " SET cpf = ?, senha = ?, cargo = ?, nome = ?, email = ?, endereco = ? WHERE id = ?";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            inserirDadosNoSql(funcionario, preparedStatement);
            preparedStatement.setLong(7, funcionario.getId());
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

    public Funcionario buscarPorCpf(String cpf) {
        return DALUtils.pegarPrimeiroDaListaOuNulo(buscar("cpf = '" + cpf + "'"));
    }

    public List<Funcionario> buscar(String criterio) {
        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE " + criterio;
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();

            List<Funcionario> funcionarios = new ArrayList<>();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                funcionarios.add(converterResultSetParaFuncionario(resultSet));
            }

            return funcionarios;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void inserirDadosNoSql(Funcionario funcionario, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, funcionario.getCpf());
        preparedStatement.setString(2, funcionario.getSenha());
        preparedStatement.setString(3, funcionario.getCargo().toString());
        preparedStatement.setString(4, funcionario.getNome());
        preparedStatement.setString(5, funcionario.getEmail());
        preparedStatement.setString(6, funcionario.getEndereco());
    }

    private Funcionario converterResultSetParaFuncionario(ResultSet resultSet) throws SQLException {
        Funcionario funcionario = new Funcionario();

        funcionario.setId(resultSet.getLong("id"));
        funcionario.setCpf(resultSet.getString("cpf"));
        funcionario.setSenha(resultSet.getString("senha"));
        funcionario.setCargo(Cargo.valueOf(resultSet.getString("cargo")));
        funcionario.setNome(resultSet.getString("nome"));
        funcionario.setEmail(resultSet.getString("email"));
        funcionario.setEndereco(resultSet.getString("endereco"));

        return funcionario;
    }
}

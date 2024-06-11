package org.example.ecommerce.dal;

import org.example.ecommerce.dal.utils.DALUtils;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAL {
    private final String NOME_TABELA = "produto";

    public Produto inserir(Produto produto) {
        String sql = "INSERT INTO " + NOME_TABELA + " (nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";

        return DALUtils.executarSql(sql, preparedStatement -> {
            inserirDadosNoSql(produto, preparedStatement);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                produto.setId(resultSet.getLong(1));
            }

            return produto;
        });
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE " + NOME_TABELA + " SET nome = ?, descricao = ?, preco = ?, quantidade = ? WHERE id = ?";

        return Boolean.TRUE.equals(DALUtils.executarSql(sql, preparedStatement -> {
            inserirDadosNoSql(produto, preparedStatement);
            preparedStatement.setLong(5, produto.getId());
            preparedStatement.executeUpdate();

            return true;
        }));
    }

    public boolean deletar(long id) {
        return DALUtils.deletar(NOME_TABELA, id);
    }

    public List<Produto> buscarPorNome(String nome) {
        return buscar("nome LIKE '%" + nome + "%'");
    }

    public List<Produto> buscar() {
        return buscar("1 = 1");
    }

    public List<Produto> buscar(String criterio) {
        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE " + criterio;
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeQuery();

            List<Produto> produtos = new ArrayList<>();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                produtos.add(converterResultSetParaProduto(resultSet));
            }

            return produtos;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void inserirDadosNoSql(Produto produto, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, produto.getNome());
        preparedStatement.setString(2, produto.getDescricao());
        preparedStatement.setDouble(3, produto.getPreco());
        preparedStatement.setInt(4, produto.getQuantidadeEstoque());
    }

    private Produto converterResultSetParaProduto(ResultSet resultSet) throws SQLException {
        Produto produto = new Produto();

        produto.setId(resultSet.getLong("id"));
        produto.setNome(resultSet.getString("nome"));
        produto.setDescricao(resultSet.getString("descricao"));
        produto.setPreco(resultSet.getDouble("preco"));
        produto.setQuantidadeEstoque(resultSet.getInt("quantidade"));

        return produto;
    }
}

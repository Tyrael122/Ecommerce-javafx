package org.example.ecommerce.dal;

import org.example.ecommerce.dal.utils.DALUtils;
import org.example.ecommerce.database.Database;
import org.example.ecommerce.model.Cliente;
import org.example.ecommerce.model.Produto;
import org.example.ecommerce.model.Venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class VendaDAL {
    private final String NOME_TABELA_VENDA = "venda";
    private final String NOME_TABELA_VENDA_ITENS = "venda_itens";

    public boolean insert(Venda venda) {
        String sql = "INSERT INTO " + NOME_TABELA_VENDA + " (data, cliente_id, funcionario_id, valor_total, finalizada) VALUES (?, ?, ?, ?, ?)";

        return Boolean.TRUE.equals(DALUtils.executarSql(sql, preparedStatement -> {
            preparedStatement.setObject(1, venda.getData());

            adicionarIdCliente(venda, preparedStatement);

            preparedStatement.setLong(3, venda.getFuncionario().getId());
            preparedStatement.setDouble(4, venda.getValorTotal());
            preparedStatement.setBoolean(5, venda.isFinalizada());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                venda.setId(resultSet.getLong(1));
            }

            return insertVendaItens(venda);
        }));
    }

    private void adicionarIdCliente(Venda venda, PreparedStatement preparedStatement) throws SQLException {
        if (venda.getCliente() == null) {
            preparedStatement.setNull(2, java.sql.Types.BIGINT);
            return;
        }

        preparedStatement.setLong(2, venda.getCliente().getId());
    }

    private boolean insertVendaItens(Venda venda) {
        if (venda.getId() == 0) {
            throw new IllegalArgumentException("Venda não possui ID");
        }

        Map<Produto, Integer> quantidadePorProduto = venda.getCarrinho().calcularQuantidadePorProduto();

        String sql = "INSERT INTO " + NOME_TABELA_VENDA_ITENS + " (venda_id, produto_id, quantidade) VALUES (?, ?, ?)";
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Produto produto : venda.getCarrinho().getProdutos()) {
                preparedStatement.setLong(1, venda.getId());
                preparedStatement.setLong(2, produto.getId());

                int quantidade = quantidadePorProduto.get(produto);
                preparedStatement.setInt(3, quantidade);

                preparedStatement.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private long getClienteId(Cliente cliente) {
        if (cliente == null) {
            return 0;
        }

        return cliente.getId();
    }
}

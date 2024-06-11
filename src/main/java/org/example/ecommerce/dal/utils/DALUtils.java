package org.example.ecommerce.dal.utils;

import org.example.ecommerce.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DALUtils {
    public static boolean deletar(String tabela, long id) {
        String sql = "DELETE FROM " + tabela + " WHERE id = ?";

        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> T executarSql(String sql, SqlRunner<PreparedStatement, T> function) {
        try (Connection connection = Database.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            return function.apply(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    public static <T> T pegarPrimeiroDaListaOuNulo(List<T> lista) {
        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.getFirst();
        }
    }
}

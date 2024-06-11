package org.example.ecommerce.dal.utils;

import java.sql.SQLException;

public interface SqlRunner<T, V> {
    V apply(T t) throws SQLException;
}

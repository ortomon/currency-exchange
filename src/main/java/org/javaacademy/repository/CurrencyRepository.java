package org.javaacademy.repository;

import org.javaacademy.model.entity.Currency;
import org.javaacademy.util.ConfiguredDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository {
    private final DataSource dataSource = ConfiguredDataSource.getInstance();

    public List<Currency> findAll() throws SQLException {
        final String query = "SELECT * FROM currencies";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query); // Создаем SQL-запрос
            statement.execute(); // Выполняем его
            ResultSet resultSet = statement.getResultSet(); // Получаем результаты

            List<Currency> currencyList = new ArrayList<>();
            while (resultSet.next()) { // Перебираем строки результата
                currencyList.add(getCurrency(resultSet)); // Преобразуем их в объекты Currency
            }
            return currencyList;
        }
    }

    public Optional<Currency> findByCode(String code) throws SQLException {
        final String query = "SELECT * FROM currencies WHERE code = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);  // Передаем значение в SQL-запрос
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(getCurrency(resultSet));
        }
    }

    public Integer save(Currency entity) throws SQLException {
        final String query = "INSERT INTO currencies (code, full_name, sign) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getFullName());
            statement.setString(3, entity.getSign());

            statement.execute();

            ResultSet savedCurrency = statement.getGeneratedKeys();
            savedCurrency.next();
            Integer savedId = savedCurrency.getInt("id");

            connection.commit();

            return savedId;
        }
    }


    private static Currency getCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getInt("id"),
                resultSet.getString("code"),
                resultSet.getString("full_name"),
                resultSet.getString("sign")
        );
    }
}

package org.javaacademy.repository;

import org.javaacademy.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCurrencyRepository {
    public List<Currency> findAll() throws SQLException {
        final String query = "SELECT * FROM currencies";

        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CurrencyExchange",
                "postgres",
                "1703")) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            List<Currency> currencyList = new ArrayList<>();
            while (resultSet.next()) {
                currencyList.add(getCurrency(resultSet));
            }
            return currencyList;
        }
    }

    public Optional<Currency> findByCode(String code) throws SQLException {
        final String query = "SELECT * FROM currencies WHERE code = ?";

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CurrencyExchange",
                "postgres",
                "1703")) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);
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

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/CurrencyExchange",
                "postgres",
                "1703")) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getFullName());
            statement.setString(3, entity.getSign());

            statement.execute();

            ResultSet savedCurrency = statement.getGeneratedKeys();
            savedCurrency.next();
            long savedId = savedCurrency.getLong("id");

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

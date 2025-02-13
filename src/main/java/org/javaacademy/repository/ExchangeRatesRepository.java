package org.javaacademy.repository;

import org.javaacademy.model.entity.Currency;
import org.javaacademy.model.entity.ExchangeRate;
import org.javaacademy.util.ConfiguredDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesRepository {
    private final DataSource dataSource = ConfiguredDataSource.getInstance();

    public List<ExchangeRate> findAll() throws SQLException {
        final String query = "SELECT * FROM ExchangeRates";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query); // Создаем SQL-запрос
            statement.execute(); // Выполняем его
            ResultSet resultSet = statement.getResultSet(); // Получаем результаты

            List<ExchangeRate> currencyList = new ArrayList<>();
            while (resultSet.next()) { // Перебираем строки результата
                currencyList.add(getExchangeRates(resultSet)); // Преобразуем их в объекты Currency
            }
            return currencyList;
        }
    }

    public Integer save(ExchangeRate entity) throws SQLException {
        final String query = "INSERT INTO ExchangeRates (baseCurrencyId, targetCurrencyId, rate) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, entity.getBaseCurrencyId());
            statement.setInt(2, entity.getTargetCurrencyId());
            statement.setBigDecimal(3, entity.getRate());

            statement.execute();

            ResultSet savedCurrency = statement.getGeneratedKeys();
            savedCurrency.next();
            Integer savedId = savedCurrency.getInt("id");

            connection.commit();

            return savedId;
        }
    }

    private static ExchangeRate getExchangeRates(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getInt("id"),
                resultSet.getInt("base_currency_id"),
                resultSet.getInt("target_currency_id"),
                resultSet.getBigDecimal("rate")
        );
    }
}

package org.javaacademy.repository;

import org.javaacademy.util.ConfiguredDataSource;

import javax.sql.DataSource;

public class ExchangeRatesRepository {
    private final DataSource dataSource = ConfiguredDataSource.getInstance();

}

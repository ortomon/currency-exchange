package org.javaacademy;

import org.javaacademy.repository.CurrencyRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        CurrencyRepository jdbcCurrencyRepository = new CurrencyRepository();

        try {
            System.out.println(jdbcCurrencyRepository.findAll());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

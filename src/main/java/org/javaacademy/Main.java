package org.javaacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaacademy.repository.JdbcCurrencyRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        JdbcCurrencyRepository jdbcCurrencyRepository = new JdbcCurrencyRepository();

        try {
            System.out.println(jdbcCurrencyRepository.findAll());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

package org.javaacademy.servlet.currency;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.javaacademy.model.Currency;
import org.javaacademy.model.response.ErrorResponse;
import org.javaacademy.repository.JdbcCurrencyRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.*;
import static org.javaacademy.util.Validation.isValidCurrencyCode;

@WebServlet(name = "CurrencyServlet", urlPatterns = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final JdbcCurrencyRepository currencyRepository = new JdbcCurrencyRepository();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String code = req.getPathInfo().replaceAll("/", "");

        if (!isValidCurrencyCode(code)) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Currency code must be in ISO 4217 format"
            ));
            return;
        }

        try {
            Optional<Currency> currencyOptional = currencyRepository.findByCode(code);

            if (currencyOptional.isEmpty()) {
                resp.setStatus(SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                        SC_NOT_FOUND,
                        "There is no such currency in the database"
                ));
                return;
            }

            objectMapper.writeValue(resp.getWriter(), currencyOptional.get());

        } catch (SQLException e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_INTERNAL_SERVER_ERROR,
                    "Something happened with the database, try again later!"
            ));
        }
    }
}

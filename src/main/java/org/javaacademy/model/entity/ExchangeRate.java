package org.javaacademy.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    private Integer id;
    @JsonProperty("base_currency_id")
    private Integer baseCurrencyId;
    @JsonProperty("target_currency_id")
    private Integer targetCurrencyId;
    private BigDecimal rate;

}

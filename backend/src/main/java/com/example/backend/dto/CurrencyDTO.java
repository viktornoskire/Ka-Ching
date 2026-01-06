package com.example.backend.dto;

import java.time.LocalDate;
import java.util.Map;

public record CurrencyDTO(LocalDate updatedAt,
                          Map<String, Map<String, Double>> exchangeRate,
                          String isoCode,
                          String name) {

    public String rate(String isoCode) {
        return exchangeRate.get(isoCode).get("rate").toString();
    }

    public String name(String isoCode) {
        return exchangeRate.get(isoCode).get("currency_name").toString();
    }
}

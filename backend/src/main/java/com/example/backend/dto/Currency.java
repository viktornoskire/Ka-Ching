package com.example.backend.dto;

import java.time.LocalDate;
import java.util.Map;

public record Currency(LocalDate updated_date,
                       Map<String, Map<String, String>> rates,
                       String isoCode,
                       String name) {

    public String rate(String isoCode) {
        return rates.get(isoCode).get("rate");
    }

    public String name(String isoCode) {
        return rates.get(isoCode).get("currency_name");
    }
}

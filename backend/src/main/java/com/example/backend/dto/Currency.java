package com.example.backend.dto;

import java.time.LocalDate;
import java.util.Map;

public record Currency(LocalDate updated_date,
                       Map<String, Map<String, String>> rates,
                       String isoCode,
                       String name) {

    public String rate(String isoCode) {
        Map<String, String> entry = rates.get(isoCode);
        if (entry == null) {
            return null;
        }
        return entry.get("rate");
    }

    public String name(String isoCode) {
        Map<String, String> entry = rates.get(isoCode);
        if (entry == null) {
            return null;
        }
        return entry.get("currency_name");
    }
}

package com.example.backend.dto;

import java.time.LocalDate;

public record CurrencyResponse(LocalDate updated_date, double rate, String isoCode, String name) {
}

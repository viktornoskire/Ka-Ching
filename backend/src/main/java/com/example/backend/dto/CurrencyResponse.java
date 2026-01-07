package com.example.backend.dto;

import java.time.LocalDate;

public record CurrencyResponse(LocalDate updated_date, String rate, String isoCode, String name) {
}

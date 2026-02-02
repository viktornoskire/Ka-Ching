package com.example.backend.dto;

import java.util.Map;

public record CurrenciesResponse(Map<String, String> currencies, String status ) {
}

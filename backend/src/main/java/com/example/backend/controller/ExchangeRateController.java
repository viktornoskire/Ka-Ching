package com.example.backend.controller;

import com.example.backend.courier.ExchangeRateService;
import com.example.backend.dto.CurrencyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class ExchangeRateController {

    ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService){
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public List<CurrencyResponse> getRates(){
        try {
            return exchangeRateService.updateRates();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to fetch currency rates:");
        }
    }
}

package com.example.backend.controller;

import com.example.backend.courier.ExchangeRateClient;
import com.example.backend.courier.ExchangeRateService;
import com.example.backend.dto.CurrencyResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class ExchangeRateController {

    ExchangeRateService exchangeRateService;
    ExchangeRateClient exchangeRateClient;

    public ExchangeRateController(ExchangeRateService exchangeRateService, ExchangeRateClient exchangeRateClient){
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateClient = exchangeRateClient;
    }

    @GetMapping
    public List<CurrencyResponse> getRates(){
        try {
            return exchangeRateService.updateRates();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to fetch currency rates:");
        }
    }

    @GetMapping("{id}")
    public CurrencyResponse getRateByIsoCode(@PathVariable String id) {
        return exchangeRateClient.getCurrency(id);
    }
}

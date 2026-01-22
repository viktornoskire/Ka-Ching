package com.example.backend.controller;

import com.example.backend.courier.ExchangeRateClient;
import com.example.backend.dto.CurrencyResponse;
import com.example.backend.dto.NameIsoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
@CrossOrigin(origins = "*")
public class ExchangeRateController {

    private final ExchangeRateClient exchangeRateClient;

    public ExchangeRateController(ExchangeRateClient exchangeRateClient){
        this.exchangeRateClient = exchangeRateClient;
    }

    @GetMapping("update")
    public void updateCurrencyRates() throws InterruptedException {
        exchangeRateClient.updateCurrencyRates();
    }

    @GetMapping
    public List<NameIsoResponse> getCurrencies(){
        return exchangeRateClient.getCurrencies();
    }

    @GetMapping("{id}")
    public CurrencyResponse getRateByIsoCode(@PathVariable String id) {
        return exchangeRateClient.getCurrency(id);
    }
}

package com.example.backend.controller;

import com.example.backend.courier.ExchangeRateClient;
import com.example.backend.dto.CurrencyResponse;
import com.example.backend.dto.NameIsoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class ExchangeRateController {

    private final ExchangeRateClient exchangeRateClient;

    public ExchangeRateController(ExchangeRateClient exchangeRateClient){
        this.exchangeRateClient = exchangeRateClient;
    }

    @Value("${app.cron-api-key}")
    private String cronSecret;

    @GetMapping("update")
    public void updateCurrencyRates(
            @RequestHeader("X-CRON-SECRET") String secret
    ) throws InterruptedException {

        if (!secret.equals(cronSecret)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        exchangeRateClient.updateCurrencyRates();
    }

    @CrossOrigin(origins = "https://ka-ching-3616.onrender.com/")
    @GetMapping
    public List<NameIsoResponse> getCurrencies(){
        return exchangeRateClient.getCurrencies();
    }

    @CrossOrigin(origins = "https://ka-ching-3616.onrender.com/")
    @GetMapping("{id}")
    public CurrencyResponse getRateByIsoCode(@PathVariable String id) {
        return exchangeRateClient.getCurrency(id);
    }
}

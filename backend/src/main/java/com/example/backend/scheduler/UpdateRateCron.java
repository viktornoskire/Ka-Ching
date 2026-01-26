package com.example.backend.scheduler;

import com.example.backend.courier.ExchangeRateClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("cron")
public class UpdateRateCron implements CommandLineRunner {

    private final ExchangeRateClient exchangeRateClient;

    public UpdateRateCron(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    public void run(String... args) throws Exception {
        exchangeRateClient.updateCurrencyRates();
        System.exit(0);
    }
}
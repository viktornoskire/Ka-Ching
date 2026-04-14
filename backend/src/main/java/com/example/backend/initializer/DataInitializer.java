package com.example.backend.initializer;

import com.example.backend.courier.ExchangeRateClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final ExchangeRateClient client;

    public DataInitializer(ExchangeRateClient client) {
        this.client = client;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
           client.updateCurrencyRates();
    }
}

package com.example.backend.scheduler;

import com.example.backend.courier.ExchangeRateClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("cron")
public class UpdateRateCron implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(UpdateRateCron.class);

    private final ExchangeRateClient exchangeRateClient;

    public UpdateRateCron(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        log.info("Cron job started: updating currency rates");

        try {
            exchangeRateClient.updateCurrencyRates();
            log.info("Cron job finished successfully");
        } catch (Exception e) {
            log.error("Cron job failed", e);
            throw e;
        }
    }
}

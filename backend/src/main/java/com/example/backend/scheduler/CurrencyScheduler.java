package com.example.backend.scheduler;

import com.example.backend.courier.ExchangeRateClient;
import com.example.backend.courier.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
public class CurrencyScheduler {
     private final ExchangeRateClient exchangeRateClient;
     private final Logger log;

     public CurrencyScheduler(ExchangeRateClient exchangeRateClient) {
         this.exchangeRateClient = exchangeRateClient;
         log = LoggerFactory.getLogger(CurrencyScheduler.class);
     }

     // Retry annotation
    @Scheduled(cron = "${scheduler.currency-fetch-cron}", zone = "Europe/Stockholm")
    public void fetchRates() {
        try {
            exchangeRateClient.updateCurrencyRates();
            log.info("Currency rates updated");
        } catch (/* InterruptedException e */ Exception er) {
            throw new RuntimeException("Failed to update currency rates:");
        }
    }
}

package com.example.backend.scheduler;

import com.example.backend.courier.ExchangeRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyScheduler {
     private final ExchangeRateService exchangeRateService;

     public CurrencyScheduler(ExchangeRateService exchangeRateService) {
         this.exchangeRateService = exchangeRateService;
     }

    @Scheduled(cron = "${scheduler.currency-fetch-cron}")
    public void fetchRates() {
        try {
            exchangeRateService.updateRates();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to update currency rates:");
        }
    }
}

package com.example.backend.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyScheduler {
    // private final ExchangeRateService exchangeRateService;

    // public CurrencyScheduler(ExchangeRateService exchangeRateService) {
    //     this.exchangeRateService = exchangeRateService;
    // }

    @Scheduled(cron = "${scheduler.currency-fetch-cron}")
    public void fetchRates() {
        // exchangeRateService.updateRates();
    }
}

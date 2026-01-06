package com.example.backend;

import com.example.backend.entity.Currency;
import com.example.backend.repository.CurrencyRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    CurrencyRepository currencyRepository;

    public DataInitializer(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       if (currencyRepository.count() == 0) {
           Currency eur = new Currency("EUR", "Euro", 1.0, LocalDate.now());
           Currency usd = new Currency("USD", "US Dollar", 1.12, LocalDate.now());
           currencyRepository.saveAll(List.of(eur, usd));
       }
    }
}

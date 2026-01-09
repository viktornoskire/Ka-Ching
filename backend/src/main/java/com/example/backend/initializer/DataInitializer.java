package com.example.backend.initializer;

import com.example.backend.entity.Currency;
import com.example.backend.repository.CurrencyRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
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
    @Profile(value = "init")
    public void run(ApplicationArguments args) throws Exception {
           Currency eur = new Currency("EUR", "Euro", 1.0, LocalDate.now());
           Currency usd = new Currency("USD", "US Dollar", 1.12, LocalDate.now());
           Currency gbp = new Currency("GBP", "British Pound", 0.84, LocalDate.now());
           Currency jpy = new Currency("JPY", "Japanese Yen", 165.0, LocalDate.now());
           Currency sek = new Currency("SEK", "Swedish Krona", 11.5, LocalDate.now());
           Currency chf = new Currency("CHF", "Swiss Franc", 0.95, LocalDate.now());
           Currency dkk = new Currency("DKK", "Danish Krone", 7.45, LocalDate.now());
           Currency nok = new Currency("NOK", "Norwegian Krone", 11.4, LocalDate.now());
           Currency cad = new Currency("CAD", "Canadian Dollar", 1.52, LocalDate.now());
           Currency aud = new Currency("AUD", "Australian Dollar", 1.68, LocalDate.now());
           Currency nzd = new Currency("NZD", "New Zealand Dollar", 1.82, LocalDate.now());
           
           currencyRepository.saveAll(List.of(eur, usd, gbp, jpy, sek, chf, dkk, nok, cad, aud, nzd));
    }
}

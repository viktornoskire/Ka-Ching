package com.example.backend.courier;

import com.example.backend.dto.CurrencyResponse;
import com.example.backend.dto.NameIsoResponse;
import com.example.backend.entity.Currency;
import com.example.backend.repository.CurrencyRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExchangeRateClient {
    private final ExchangeRateService exchangeRateService;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateClient(ExchangeRateService exchangeRateService, CurrencyRepository currencyRepository) {
        this.exchangeRateService = exchangeRateService;
        this.currencyRepository = currencyRepository;
    }

    public CurrencyResponse getCurrency(String iso) {
        Currency cur = currencyRepository.getCurrencyByIsoCode(iso);
        if (cur == null) {
            throw new RuntimeException("Currency not found");
        }
        return new CurrencyResponse(cur.getRecordedAt(), cur.getRate(), cur.getIsoCode(), cur.getName());
    }

    public List<NameIsoResponse> getCurrencies() {
        return currencyRepository.getAllLatestCurrencies().stream()
                .map(c -> new NameIsoResponse(c.getName(), c.getIsoCode()))
                .toList();
    }

    public void updateCurrencyRates() throws InterruptedException {
        exchangeRateService.updateRates();
    }
}

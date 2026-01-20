package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import com.example.backend.dto.Currency;
import com.example.backend.dto.CurrencyResponse;
import com.example.backend.error.ApiUsageLimit;
import com.example.backend.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {
    Logger log;

    @Value("${app.API_KEY}")
    private String API_KEY;

    @Value("${app.API_KEY_FALLBACK:}")
    private String API_KEY_FALLBACK;

    private String ACTIVE_KEY = null;

    private final WebClient webClient;
    private final CurrencyListService currencyListService;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateService(WebClient.Builder builder, CurrencyListService currencyListService, CurrencyRepository currencyRepository) {
        this.webClient = builder.baseUrl("https://api.getgeoapi.com/v2/currency/convert").build();
        this.currencyListService = currencyListService;
        this.currencyRepository = currencyRepository;
        log = org.slf4j.LoggerFactory.getLogger(ExchangeRateService.class);
    }

    public void updateRates() throws InterruptedException {
        CurrenciesResponse list = currencyListService.getCurrenciesList();
        List<CurrencyResponse> currencies = new ArrayList<>();
        for (String isoCode : list.currencies().keySet()) {
            Thread.sleep(1000); // sleep for 1 second between requests to not overload the API
            Currency cur = fetchCurrency(isoCode);

            if (cur == null) {
                throw new RuntimeException("Failed to fetch currency rates");
            }

            LocalDate updatedDate = cur.updated_date();
            double rate = Double.parseDouble(cur.rate(isoCode));
            String name = cur.name(isoCode);

            if (rate == 0.0 || name == null || updatedDate == null) {
                log.warn("Invalid currency data: {}", cur);
                continue;
            }
            try {
                currencyRepository.saveCurrency(new com.example.backend.entity.Currency(isoCode, name, rate, updatedDate));
            } catch (NumberFormatException e) {
                log.warn("Invalid currency rate: {}", cur);
            }
        }
    }

    private Currency fetchCurrency(String isoCode) {
        if (ACTIVE_KEY == null) {
            ACTIVE_KEY = API_KEY;
        }
        try {
           return callApi(ACTIVE_KEY, isoCode);
        } catch (ApiUsageLimit e) {
            if (API_KEY_FALLBACK == null || API_KEY_FALLBACK.isBlank()) {
                throw e;
            }
            log.warn("Primary API key exhausted, falling back to secondary key");
           try {
               ACTIVE_KEY = API_KEY_FALLBACK;
               return callApi(ACTIVE_KEY, isoCode);
           } catch (ApiUsageLimit e2) {
               throw new ApiUsageLimit("Both API keys exhausted, aborting");
           }
        }
    }

    private Currency callApi(String apiKey, String isoCode) {
        return webClient.get().uri(uri ->
                        uri
                                .queryParam("api_key", apiKey)
                                .queryParam("to", isoCode)
                                .queryParam("format", "json")
                                .build()
                )
                .retrieve()
                .onStatus(status -> status.value() == 403 || status.value() == 429,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> reactor.core.publisher.Mono.error(
                                        new ApiUsageLimit(body)
                                ))
                )
                .bodyToMono(Currency.class)
                .block();
    }
}

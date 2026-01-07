package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import com.example.backend.dto.Currency;
import com.example.backend.dto.CurrencyResponse;
import com.example.backend.error.ApiUsageLimit;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public ExchangeRateService(WebClient.Builder builder, CurrencyListService currencyListService) {
        this.webClient = builder.baseUrl("https://api.getgeoapi.com/v2/currency/convert").build();
        this.currencyListService = currencyListService;
        log = org.slf4j.LoggerFactory.getLogger(ExchangeRateService.class);
    }

    public List<CurrencyResponse> updateRates() throws InterruptedException {
        CurrenciesResponse list = currencyListService.getCurrenciesList();
        List<CurrencyResponse> currencies = new ArrayList<>();
        for (String isoCode : list.currencies().keySet()) {
            Thread.sleep(1000);
            Currency cur = fetchCurrency(isoCode);
            currencies.add(new CurrencyResponse(cur.updated_date(), cur.rate(isoCode), isoCode, cur.name(isoCode)));
        }
        return currencies;
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

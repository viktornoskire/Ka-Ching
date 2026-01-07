package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import com.example.backend.dto.Currency;
import com.example.backend.dto.CurrencyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {
    @Value("${app.API_KEY}")
    private String API_KEY;

    @Value("${app.API_KEY_FALLBACK:}")
    private String API_KEY_FALLBACK;

    private final WebClient webClient;
    private final CurrencyListService currencyListService;

    public ExchangeRateService(WebClient.Builder builder, CurrencyListService currencyListService) {
        this.webClient = builder.baseUrl("https://api.getgeoapi.com/v2/currency/convert").build();
        this.currencyListService = currencyListService;
    }

    public List<CurrencyResponse> updateRates() {
        CurrenciesResponse list = currencyListService.getCurrenciesList();
        List<CurrencyResponse> currencies = new ArrayList<>();
        int i = 0;
        for (String isoCode : list.currencies().keySet()) {
            i++;
            Currency cur = webClient.get().uri(uri ->
                            uri
                                    .queryParam("api_key", API_KEY)
                                    .queryParam("to", isoCode)
                                    .queryParam("format", "json")
                                    .build()
                    )
                    .retrieve()
                    .bodyToMono(Currency.class)
                    .block();
            currencies.add(new CurrencyResponse(cur.updated_date(), cur.rate(isoCode), isoCode, cur.name(isoCode)));
            if (i == 3) {
                return currencies;
            }
        }
        return currencies;
    }
}

package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import com.example.backend.dto.Currency;
import com.example.backend.error.ApiUsageLimit;
import com.example.backend.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExchangeRateService {

    private static final Logger log =
            LoggerFactory.getLogger(ExchangeRateService.class);

    @Value("${app.api-key}")
    private String apiKey;

    @Value("${app.api-key-fallback:}")
    private String apiKeyFallback;

    @Value("${app.external-api-base-url}")
    private String baseUrl;

    private String activeKey;

    private WebClient webClient;

    private final CurrencyListService currencyListService;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateService(
            CurrencyListService currencyListService,
            CurrencyRepository currencyRepository
    ) {
        this.currencyListService = currencyListService;
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    void init() {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException(
                    "External API base URL is missing (MY_EXTERNAL_API_URL)"
            );
        }

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl + "/convert")
                .build();

        this.activeKey = apiKey;

        log.info("External API base URL = {}", baseUrl);
    }

    public void updateRates() throws InterruptedException {
        CurrenciesResponse list = currencyListService.getCurrenciesList();
        List<String> isoCodes = new ArrayList<>(list.currencies().keySet());

        for (String isoCode : isoCodes) {
            Thread.sleep(1000); // avoid API throttling

            Currency cur = fetchCurrency(isoCode);
            if (cur == null) {
                throw new RuntimeException("Failed to fetch currency: " + isoCode);
            }

            LocalDate updatedDate = cur.updated_date();
            double rate = Double.parseDouble(cur.rate(isoCode));
            String name = cur.name(isoCode);

            if (rate == 0.0 || name == null || updatedDate == null) {
                log.warn("Invalid currency data for {}", isoCode);
                continue;
            }

            currencyRepository.save(
                    new com.example.backend.entity.Currency(
                            isoCode, name, rate, updatedDate
                    )
            );
        }
    }

    private Currency fetchCurrency(String isoCode) {
        try {
            return callApi(activeKey, isoCode);
        } catch (ApiUsageLimit e) {
            if (apiKeyFallback == null || apiKeyFallback.isBlank()) {
                throw e;
            }

            log.warn("Primary API key exhausted, switching to fallback");
            activeKey = apiKeyFallback;
            return callApi(activeKey, isoCode);
        }
    }

    private Currency callApi(String apiKey, String isoCode) {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("api_key", apiKey)
                        .queryParam("to", isoCode)
                        .queryParam("format", "json")
                        .build()
                )
                .retrieve()
                .onStatus(
                        status -> status.value() == 403 || status.value() == 429,
                        response -> response.bodyToMono(String.class)
                                .map(ApiUsageLimit::new)
                )
                .bodyToMono(Currency.class)
                .block();
    }
}

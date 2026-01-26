package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyListService {

    private static final Logger log =
            LoggerFactory.getLogger(CurrencyListService.class);

    @Value("${app.api-key}")
    private String apiKey;

    @Value("${app.external-api-base-url}")
    private String baseUrl;

    private WebClient webClient;

    public CurrencyListService() {
        // no logic here
    }

    @PostConstruct
    void init() {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException(
                    "External API base URL is missing (MY_EXTERNAL_API_URL)"
            );
        }

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "API key is missing (MY_API_KEY)"
            );
        }

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl + "/list")
                .build();

        log.info("Currency list API base URL = {}", baseUrl);
    }

    public CurrenciesResponse getCurrenciesList() {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("api_key", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(CurrenciesResponse.class)
                .block();
    }
}

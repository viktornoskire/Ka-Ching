package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyListService {
    String API_KEY = "28048517cb8876b8cf974a2e4446efea001dbbb2";
    String API_KEY_FALLBACK = "5c2654767f8763adf1e5928bcab34f0a36a7a25a";

    private final WebClient webClient;

    public CurrencyListService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.getgeoapi.com/v2/currency/list").build();
    }

    public CurrenciesResponse getCurrenciesList() {
        CurrenciesResponse curList = webClient.get().uri(uri ->
                        uri
                                .queryParam("api_key", API_KEY)
                                .build()
                )
                .retrieve()
                .bodyToMono(CurrenciesResponse.class)
                .block();
        return curList;
    }

}

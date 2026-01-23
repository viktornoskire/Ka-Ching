package com.example.backend.courier;

import com.example.backend.dto.CurrenciesResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CurrencyListService {

    @Value("${app.API_KEY}")
    private String API_KEY;

    @Value("${app.API_KEY_FALLBACK:}")
    private String API_KEY_FALLBACK;

    @Value("${app.EXTERNAL_API_BASE_URL}")
    private String baseUrl;

    @PostConstruct
    void test() {
        System.out.println(API_KEY);
    }


    private final WebClient webClient;

    public CurrencyListService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(baseUrl + "/list").build();
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

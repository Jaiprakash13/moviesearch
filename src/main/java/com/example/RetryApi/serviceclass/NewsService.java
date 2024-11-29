package com.example.RetryApi.serviceclass;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NewsService {

    @Value("${mediastack.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "newsApiRetry", fallbackMethod = "fallbackMethod")
    public String fetchNewsByCategory(String category) {
        // Build the URL with query parameters
        String url = UriComponentsBuilder.fromUriString("http://api.mediastack.com/v1/news")
                .queryParam("access_key", apiKey)
                .queryParam("categories", category)
                .toUriString();

        // Make the HTTP GET request
        return restTemplate.getForObject(url, String.class);
    }

    public String fallbackMethod(String category, Throwable t) {
        return "News for category '" + category + "' is currently unavailable. Please try again later.";
    }
}

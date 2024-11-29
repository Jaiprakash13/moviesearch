package com.example.RetryApi.serviceclass;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MovieService {

    @Value("${omdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "movieApiRetry", fallbackMethod = "fallbackMethod")
    public String fetchMovieDetails(String movieTitle) {
        // Build the URL with the movie title and API key
        String url = UriComponentsBuilder.fromUriString("http://www.omdbapi.com/")
                .queryParam("apikey", apiKey)
                .queryParam("t", movieTitle)
                .toUriString();

        // Make the HTTP GET request to fetch the movie details
        return restTemplate.getForObject(url, String.class);
    }

    // Fallback method in case of failure
    public String fallbackMethod(String movieTitle, Throwable t) {
        return "Movie information for " + movieTitle + " is currently unavailable. Please try again later.";
    }
}

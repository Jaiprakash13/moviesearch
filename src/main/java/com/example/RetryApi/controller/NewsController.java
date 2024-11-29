package com.example.RetryApi.controller;

import com.example.RetryApi.serviceclass.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public String getNews(@RequestParam(required = false, defaultValue = "general") String category) {
        return newsService.fetchNewsByCategory(category);
    }
}

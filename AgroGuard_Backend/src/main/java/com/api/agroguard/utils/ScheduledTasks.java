package com.api.agroguard.utils;

import com.api.agroguard.service.NewsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ScheduledTasks {

    private final NewsService newsService;

    public ScheduledTasks(NewsService newsService) {
        this.newsService = newsService;
    }

    @Scheduled(fixedRate = 86400000) // 24 hours in milliseconds
    public void reportCurrentTime() throws ExecutionException, InterruptedException {
        newsService.fetchAgricultureNews();
    }
}

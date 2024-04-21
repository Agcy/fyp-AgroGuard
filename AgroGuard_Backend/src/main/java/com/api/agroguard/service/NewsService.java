package com.api.agroguard.service;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class NewsService {

    private final NewsApiClient newsApiClient;

    public NewsService() {
        this.newsApiClient = new NewsApiClient("52b4a91b8516489da301f46970d5cca9");
    }

    public List<Article> fetchAgricultureNews() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Article>> futureArticles = new CompletableFuture<>();

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("agriculture")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        futureArticles.complete(response.getArticles());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        futureArticles.completeExceptionally(throwable);
                    }
                }
        );

        return futureArticles.get();
    }
}

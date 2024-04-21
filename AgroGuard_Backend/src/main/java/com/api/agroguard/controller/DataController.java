package com.api.agroguard.controller;

import com.api.agroguard.entity.AgricultureData;
import com.api.agroguard.entity.PriceData;
import com.api.agroguard.entity.ProjData;
import com.api.agroguard.service.AgricultureDataService;
import com.api.agroguard.service.NewsService;
import com.kwabenaberko.newsapilib.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DataController {
    @Autowired
    private AgricultureDataService agricultureDataService;

    private final NewsService newsService;

    public DataController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/supply-demand/agriculture-data")
    public List<AgricultureData> getAgricultureData(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String commodity,
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String period) {
        return agricultureDataService.fetchData(region, commodity, year, period);
    }

    @GetMapping("/price/agricultural-price")
    public ResponseEntity<List<PriceData>> getPriceData(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(agricultureDataService.fetchPriceData(type, category));
    }

    @GetMapping("/supply-demand/agricultural-proj")
    public ResponseEntity<List<ProjData>> getProjData(
            @RequestParam(required = false) String report_month,
            @RequestParam(required = false) String commodity,
            @RequestParam(required = false) String item){
        return ResponseEntity.ok(agricultureDataService.fetchProjData(report_month, commodity, item));
    }

    @GetMapping("/news/agriculture-news-data")
    public ResponseEntity<List<Article>> getNewsData() {
        try {
            List<Article> articles = newsService.fetchAgricultureNews();
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}


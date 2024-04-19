package com.api.agroguard.controller;

import com.api.agroguard.entity.Commodity;
import com.api.agroguard.entity.DataPoint;
import com.api.agroguard.entity.TimeSeriesData;
import com.api.agroguard.service.CommodityService;
import com.api.agroguard.service.DataService;
import com.api.agroguard.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DataController {
    private final DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/agriculture/data")
    public Mono<List<Map<String, Object>>> fetchData(@RequestParam String code) {
        System.out.println("Fetching data for code: " + code);
        return dataService.fetchData(code);
    }

}


package com.api.agroguard.service;

import com.api.agroguard.entity.DataPoint;
import com.api.agroguard.entity.TimeSeriesData;
import com.api.agroguard.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public DataService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("https://data.nasdaq.com").build();
        this.objectMapper = objectMapper;
    }

    public Mono<List<Map<String, Object>>> fetchData(String code) {
        String apiKey = "yCEncD3NhywTNWUFX3aL";
        System.out.println("before fetching data for code: " + code);
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v3/datatables/WASDE/DATA")
                        .queryParam("code", code)
                        .queryParam("report_month", "2024-02")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .map(this::convertToDataPoints);
    }

    private List<Map<String, Object>> convertToDataPoints(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode columnsNode = root.path("datatable").path("columns");
            JsonNode dataNode = root.path("datatable").path("data");

            List<Map<String, Object>> dataPoints = new ArrayList<>();
            if (dataNode.isArray()) {
                for (JsonNode rowNode : dataNode) {
                    if (rowNode.isArray()) {
                        Map<String, Object> dataPoint = new HashMap<>();
                        for (int i = 0; i < rowNode.size(); i++) {
                            String key = columnsNode.get(i).get("name").asText();
                            JsonNode valueNode = rowNode.get(i);
                            Object value = valueNode.isNumber() ? valueNode.numberValue() : valueNode.asText();
                            dataPoint.put(key, value);
                        }
                        dataPoints.add(dataPoint);
                    }
                }
            }
            return dataPoints;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse data", e);
        }
    }
}

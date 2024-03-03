package com.api.agroguard.service.impl;

import com.api.agroguard.service.GeoLocationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    @Value("${ipgeolocation.api.key}")
    private String apiKey;
    @Override
    public String getGeoLocation(String ip) {
        // 检查是否是本地回环地址
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return "Localhost";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.ipgeolocation.io/ipgeo?apiKey=%s&ip=%s", apiKey, ip);
        try {
            GeoLocationResponse response = restTemplate.getForObject(url, GeoLocationResponse.class);
            // 假设API返回的响应中有一个名为country_name的字段
            return response != null ? response.getCountryName() : "Unknown";
        } catch (Exception e) {
            // 在这里处理异常，例如记录日志
            System.out.println("Error getting geolocation: " + e.getMessage());
            return "Error";
        }
    }

    // 假设的响应类，根据实际API响应结构调整
    private static class GeoLocationResponse {
        private String countryName;

        // Getters and Setters
        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }
    }
}

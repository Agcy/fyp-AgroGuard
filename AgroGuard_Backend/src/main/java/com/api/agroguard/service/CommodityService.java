package com.api.agroguard.service;

import com.api.agroguard.entity.Commodity;

import java.util.List;

public interface CommodityService {
    List<Commodity> getCommoditiesByRegion(String region);
}

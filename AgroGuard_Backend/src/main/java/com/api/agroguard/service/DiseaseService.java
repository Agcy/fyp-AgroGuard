package com.api.agroguard.service;

import com.api.agroguard.model.DiseaseDO;

import java.util.List;

public interface DiseaseService {
    List<DiseaseDO> findAllDiseases();
    DiseaseDO findDiseaseById(String id);
}

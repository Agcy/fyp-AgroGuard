package com.api.agroguard.service.impl;

import com.api.agroguard.model.DiseaseDO;
import com.api.agroguard.repository.DiseaseRepository;
import com.api.agroguard.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    private final DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseServiceImpl(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    @Override
    public List<DiseaseDO> findAllDiseases() {
        return diseaseRepository.findAll();
    }

    @Override
    public DiseaseDO findDiseaseById(String id) {
        return diseaseRepository.findDiseaseById(id);
    }
}

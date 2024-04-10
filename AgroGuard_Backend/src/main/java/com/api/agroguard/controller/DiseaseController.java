package com.api.agroguard.controller;

import com.api.agroguard.model.DiseaseDO;
import com.api.agroguard.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/diseases")
public class DiseaseController {
    private final DiseaseService diseaseService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping
    public ResponseEntity<List<DiseaseDO>> getAllDiseases() {
        List<DiseaseDO> diseases = diseaseService.findAllDiseases();
        return ResponseEntity.ok(diseases);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiseaseDO> getDiseaseById(@PathVariable String id) {
        DiseaseDO disease = diseaseService.findDiseaseById(id);
        if (disease != null) {
            return ResponseEntity.ok(disease);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

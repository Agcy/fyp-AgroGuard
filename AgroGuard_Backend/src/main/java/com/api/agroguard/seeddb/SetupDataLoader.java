package com.api.agroguard.seeddb;

import com.api.agroguard.entity.ERole;
import com.api.agroguard.entity.Role;
import com.api.agroguard.model.DiseaseDO;
import com.api.agroguard.repository.DiseaseRepository;
import com.api.agroguard.repository.RoleRepository;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SetupDataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;

    @Data
    public static class DiseaseCsv {
        @CsvBindByName
        private String index;
        @CsvBindByName
        private String disease_name;
        @CsvBindByName
        private String image_url; // Base64编码的图片字符串
        @CsvBindByName
        private String description;
        @CsvBindByName
        private String prevent;

        // Getter和Setter
        public List<String> getImageBase64List() {
            return Arrays.asList(image_url.split(";;")); // 假设用";"分隔Base64编码
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource("csv/disease_info.csv").getInputStream())) {
            List<DiseaseCsv> csvDiseases = new CsvToBeanBuilder<DiseaseCsv>(reader)
                    .withType(DiseaseCsv.class)
                    .build()
                    .parse();

            List<DiseaseDO> diseases = csvDiseases.stream().map(csvDisease -> {
                DiseaseDO disease = new DiseaseDO();
                disease.setIndex(csvDisease.getIndex());
                disease.setDisease_name(csvDisease.getDisease_name());
                disease.setImage_url(csvDisease.getImageBase64List()); // 设置Base64图片数组
                disease.setDescription(csvDisease.getDescription());
                disease.setPrevent(csvDisease.getPrevent());
                return disease;
            }).collect(Collectors.toList());

            // 使用repository保存diseases到MongoDB
            diseaseRepository.saveAll(diseases);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


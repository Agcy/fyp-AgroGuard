package com.api.agroguard.service;

import com.api.agroguard.entity.AgricultureData;
import com.api.agroguard.entity.PriceData;
import com.api.agroguard.entity.ProjData;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgricultureDataService {

    public List<AgricultureData> fetchData(String region, String commodity, String year, String period) {
        String fileName = "csv/WASDE_DATA_" + commodity.toUpperCase().replace(" ", "_") + ".csv";
        System.out.println(fileName);
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource(fileName).getInputStream())) {
            List<AgricultureData> agricultureDataList = new CsvToBeanBuilder<AgricultureData>(reader)
                    .withType(AgricultureData.class)
                    .build()
                    .parse();

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return agricultureDataList.stream()
                    .filter(data ->
                            data.getReport_month().equals("2024-02") &&
                            data.getRegion().equals(region))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PriceData> fetchPriceData(String type, String category) {
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource("csv/API_csv-21Mar2024i.csv").getInputStream())) {
            List<PriceData> dataPoints = new CsvToBeanBuilder<PriceData>(reader)
                    .withType(PriceData.class)
                    .build()
                    .parse();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dataPoints.stream()
                    .filter(data -> data.getType().equalsIgnoreCase(type) &&
                            data.getCategory().equalsIgnoreCase(category))
                    .sorted(Comparator.comparing(data -> LocalDate.parse(data.getDate(), formatter), Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProjData> fetchProjData(String report_month, String commodity, String item) {
        try (InputStreamReader reader = new InputStreamReader(new ClassPathResource("csv/WASDE_PROJ_4a0228469ca655f6f40025ff0f1c1c65.csv").getInputStream())) {
            List<ProjData> dataPoints = new CsvToBeanBuilder<ProjData>(reader)
                    .withType(ProjData.class)
                    .build()
                    .parse();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dataPoints.stream()
                    .filter(data ->
                            data.getCommodity().equalsIgnoreCase(commodity.toUpperCase()) &&
                            data.getItem().equalsIgnoreCase(item) &&
                            data.getReport_month().equalsIgnoreCase(report_month) &&
                            data.getMeasure().equals("average"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

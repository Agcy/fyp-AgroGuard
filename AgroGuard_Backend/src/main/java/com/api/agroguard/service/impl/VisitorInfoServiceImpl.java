package com.api.agroguard.service.impl;

import com.api.agroguard.model.VisitorInfoDO;
import com.api.agroguard.repository.VisitorInfoRepository;
import com.api.agroguard.service.VisitorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorInfoServiceImpl implements VisitorInfoService {

    @Autowired
    private VisitorInfoRepository visitorInfoRepository;
    @Override
    public void updateVisitorInfo(String ip, String geoLocation) {
        VisitorInfoDO visitorInfo = visitorInfoRepository.findByIp(ip)
                .orElseGet(() -> new VisitorInfoDO());

        visitorInfo.setIp(ip);
        visitorInfo.setGeoLocation(geoLocation);
        visitorInfo.setVisitCount(visitorInfo.getVisitCount() + 1);

        visitorInfoRepository.save(visitorInfo);
    }
}

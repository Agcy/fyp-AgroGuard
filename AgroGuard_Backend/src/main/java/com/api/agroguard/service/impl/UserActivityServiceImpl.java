package com.api.agroguard.service.impl;

import com.api.agroguard.model.UserActivityDO;
import com.api.agroguard.repository.UserActivityRepository;
import com.api.agroguard.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActivityServiceImpl implements UserActivityService {
    @Autowired
    private UserActivityRepository repository;
    @Override
    public void logActivity(UserActivityDO activity) {
        repository.save(activity);
    }
}

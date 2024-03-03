package com.api.agroguard.service;

import com.api.agroguard.model.UserActivityDO;

public interface UserActivityService {
    void logActivity(UserActivityDO activity);
}

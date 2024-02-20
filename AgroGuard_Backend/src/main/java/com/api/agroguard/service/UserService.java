package com.api.agroguard.service;

import com.api.agroguard.model.UserDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserService {
    UserDO createUser(UserDO user);
    UserDO getUserById(String id);
    List<UserDO> getAllUsers();
    UserDO updateUser(String id, UserDO user);
    void deleteUser(String id);

}

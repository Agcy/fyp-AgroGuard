package com.api.agroguard.service.impl;
import com.api.agroguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.agroguard.model.UserDO;
import com.api.agroguard.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDO createUser(UserDO user) {
        return userRepository.save(user);
    }

    @Override
    public UserDO getUserById(String id) {
        Optional<UserDO> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<UserDO> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDO updateUser(String id, UserDO userDetails) {
        UserDO user = getUserById(id);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setGender(userDetails.getGender());
            user.setOccupation(userDetails.getOccupation());
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}

package com.api.agroguard.service.impl;
import com.api.agroguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.agroguard.model.UserDO;
import com.api.agroguard.service.UserService;
import org.springframework.stereotype.Service;
import com.api.agroguard.utils.UserValidationUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUser(UserDO user) {
        // 使用UserValidationUtil进行验证
        if (!UserValidationUtil.checkEmailFormat(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (!UserValidationUtil.checkPasswordStrength(user.getPassword())) {
            throw new IllegalArgumentException("Passwords must contain uppercase, lowercase, numbers and special characters and be 6 or more digits in length");
        }
        // 检查用户是否已存在
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        // 加密密码并保存用户
        userRepository.save(user);
        return "User registered successfully";
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

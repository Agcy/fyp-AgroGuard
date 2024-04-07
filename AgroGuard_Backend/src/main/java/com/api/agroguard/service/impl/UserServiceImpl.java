package com.api.agroguard.service.impl;
import com.api.agroguard.exception.ResourceNotFoundException;
import com.api.agroguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.api.agroguard.model.UserDO;
import com.api.agroguard.service.UserService;
import org.springframework.stereotype.Service;
import com.api.agroguard.utils.UserValidationUtil;
import com.api.agroguard.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public void followUser(String userId, String followUserId) {
        UserDO user = getUserById(userId);
        UserDO followUser = getUserById(followUserId);
        System.out.println(user.getFollowing()+" "+followUser.getFollowers());
        if (!user.getFollowing().contains(followUserId)) {
            user.getFollowing().add(followUserId);
            followUser.getFollowers().add(userId);
            System.out.println(user.getFollowing()+" "+followUser.getFollowers());
            userRepository.save(user);
            userRepository.save(followUser);
        }
    }

    @Override
    public void unfollowUser(String userId, String followUserId) {
        UserDO user = getUserById(userId);
        UserDO followUser = getUserById(followUserId);
        System.out.println(user.getFollowing()+" "+followUser.getFollowers());
        if (user.getFollowing().contains(followUserId)) {
            user.getFollowing().remove(followUserId);
            followUser.getFollowers().remove(userId);
            System.out.println(user.getFollowing()+" "+followUser.getFollowers());
            userRepository.save(user);
            userRepository.save(followUser);
        }
    }

    @Override
    public List<UserDTO> getFollowingUsers(String userId) {
        UserDO user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFollowing().stream()
                .map(followingId -> convertToDTO(userRepository.findById(followingId).orElse(null)))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getFollowers(String userId) {
        UserDO user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getFollowers().stream()
                .map(followerId -> convertToDTO(userRepository.findById(followerId).orElse(null)))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getMutualFollows(String userId) {
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));

        // 获取该用户的所有关注者（followers）和他们关注的用户（following）
        List<String> followingIds = user.getFollowing();
        List<UserDTO> mutualFollows = new ArrayList<>();

        for (String followId : followingIds) {
            UserDO followUser = userRepository.findById(followId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + followId + " not found"));

            // 检查是否互相关注
            if (followUser.getFollowers().contains(userId)) {
                mutualFollows.add(convertToDTO(followUser));
            }
        }

        return mutualFollows;
    }

    @Override
    public Boolean checkFollowsStatus(String userId, String followUserId) {
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
        return user.getFollowing().contains(followUserId);
    }


    private UserDTO convertToDTO(UserDO user) {
        // Conversion logic to turn UserDO into a simpler DTO for frontend usage, including username, avatar URL, etc.
        if (user == null) {
            return null;
        }
        String id = user.getId();
        String username = user.getName(); // Assuming getName() returns the user's username in UserDO
        String avatarUrl = user.getAvatarUrl(); // Assuming this field exists and getter method is getAvatarUrl()
        boolean isOnline = user.isOnline(); // Assuming there's a boolean field in UserDO indicating online status with a getter isOnline()

        return new UserDTO(id, username, avatarUrl, isOnline);
    }
}

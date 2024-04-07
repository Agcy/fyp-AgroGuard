package com.api.agroguard.service;

import com.api.agroguard.dto.UserDTO;
import com.api.agroguard.model.UserDO;

import java.util.List;

public interface UserService {
    String createUser(UserDO user);
    UserDO getUserById(String id);
    List<UserDO> getAllUsers();
    UserDO updateUser(String id, UserDO user);
    void deleteUser(String id);
    void followUser(String userId, String followUserId);
    void unfollowUser(String userId, String followUserId);
    List<UserDTO> getFollowingUsers(String userId);
    List<UserDTO> getFollowers(String userId);
    List<UserDTO> getMutualFollows(String userId);

    Boolean checkFollowsStatus(String userId, String followUserId);
}

package com.api.agroguard.service.impl;

import com.api.agroguard.exception.ResourceNotFoundException;
import com.api.agroguard.model.PostDO;
import com.api.agroguard.repository.PostRepository;
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

    @Autowired
    private PostRepository postRepository;

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
        System.out.println(user.getFollowing() + " " + followUser.getFollowers());
        List<String> following = user.getFollowing();
        List<String> followers = followUser.getFollowers();
        if (!user.getFollowing().contains(followUserId)) {
            following.add(followUserId);
            followers.add(userId);
            user.setFollowing(following);
            followUser.setFollowers(followers);
            System.out.println(user.getFollowing() + " " + followUser.getFollowers());
            userRepository.save(user);
            userRepository.save(followUser);
        }
    }

    @Override
    public void unfollowUser(String userId, String followUserId) {
        UserDO user = getUserById(userId);
        UserDO followUser = getUserById(followUserId);
        List<String> following = user.getFollowing();
        List<String> followers = followUser.getFollowers();
        System.out.println(user.getFollowing() + " " + followUser.getFollowers());
        if (user.getFollowing().contains(followUserId)) {
            following.remove(followUserId);
            followers.remove(userId);
            user.setFollowing(following);
            followUser.setFollowers(followers);
            System.out.println(user.getFollowing() + " " + followUser.getFollowers());
            userRepository.save(user);
            userRepository.save(followUser);
        }
    }

    @Override
    public List<UserDO> getFollowingUsers(String userId) {
        UserDO user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<String> followingIds = user.getFollowing();
        List<UserDO> FollowingUser = new ArrayList<>();
        for (String followingId : followingIds) {
            UserDO followingUser = userRepository.findById(followingId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + followingId + " not found"));
            if (followingUser != null) {
                FollowingUser.add(followingUser);
            }
        }
        return FollowingUser;
    }

    @Override
    public List<UserDO> getFollowers(String userId) {
        UserDO user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<String> followerIds = user.getFollowers();
        List<UserDO> FollowersUser = new ArrayList<>();
        for (String followerId : followerIds) {
            UserDO followerUser = userRepository.findById(followerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + followerId + " not found"));
            if (followerUser != null) {
                FollowersUser.add(followerUser);
            }
        }
        return FollowersUser;


    }


    @Override
    public List<UserDO> getMutualFollows(String userId) {
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));

        // 获取该用户的所有关注者（followers）和他们关注的用户（following）
        List<String> followingIds = user.getFollowing();
        List<UserDO> mutualFollowsUsers = new ArrayList<>();

        for (String followId : followingIds) {
            UserDO followUser = userRepository.findById(followId)
                    .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + followId + " not found"));

            // 检查是否互相关注
            if (followUser.getFollowers().contains(userId)) {
                mutualFollowsUsers.add(followUser);
            }
        }
        return mutualFollowsUsers;
    }

    @Override
    public void likePost(String userId, String postId) {
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
        List<String> likedPosts = user.getLikedPosts();
        likedPosts.add(postId);

        user.setLikedPosts(likedPosts);

        userRepository.save(user);
    }

    @Override
    public List<PostDO> getLikedPosts(String userId) {
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
        List<String> likedPostIds = user.getLikedPosts();
        List<PostDO> likedPosts = new ArrayList<>();
        System.out.println(likedPostIds);
        for (String postId : likedPostIds) {
            PostDO post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + postId + " not found")); // Assuming there's a method to get a post by ID from the repository
            if (post != null) {
                likedPosts.add(post);
            }
        }
        return likedPosts;
    }

    @Override
    public Boolean checkFollowsStatus(String userId, String followUserId) {
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + userId + " not found"));
        return user.getFollowing().contains(followUserId);
    }

    @Override
    public void unlikePost(String id, String postId) {
        UserDO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " not found"));
        List<String> likedPosts = user.getLikedPosts();
        likedPosts.remove(postId);
        user.setLikedPosts(likedPosts);
        userRepository.save(user);
    }


//    private UserDTO convertToDTO(UserDO user) {
//        // Conversion logic to turn UserDO into a simpler DTO for frontend usage, including username, avatar URL, etc.
//        if (user == null) {
//            return null;
//        }
//        String id = user.getId();
//        String username = user.getName(); // Assuming getName() returns the user's username in UserDO
//        String avatarUrl = user.getAvatarUrl(); // Assuming this field exists and getter method is getAvatarUrl()
//        boolean isOnline = user.isOnline(); // Assuming there's a boolean field in UserDO indicating online status with a getter isOnline()
//
//        return new UserDTO(id, username, avatarUrl, isOnline);
//    }
}

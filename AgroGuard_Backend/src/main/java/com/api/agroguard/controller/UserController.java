package com.api.agroguard.controller;

import com.api.agroguard.exception.ResourceNotFoundException;
import com.api.agroguard.model.UserDO;
import com.api.agroguard.repository.UserRepository;
import com.api.agroguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // get all users
    @GetMapping("/users/getAll")
    public List<UserDO> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDO loginDetails) {
        UserDO user = userRepository.findByEmail(loginDetails.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginDetails.getEmail()));

        if (Objects.equals(loginDetails.getPassword(), user.getPassword())) {
            return ResponseEntity.ok().body("User authenticated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // create user rest API
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDO user) {
        try {
            String result = userService.createUser(user);
            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get user by id rest api
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDO> getUserById(@PathVariable String id) {
        UserDO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id :" + id));
        return ResponseEntity.ok(user);
    }

    // update user rest api
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDO> updateUser(@PathVariable String id,
                                           @RequestBody UserDO userDetails) {
        UserDO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User not exist with id :" + id));
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setGmtModified(LocalDateTime.now());
        UserDO updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // delete user rest api
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser
    (@PathVariable String id) {
        UserDO user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("User not exist with id :" + id));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

package com.api.agroguard.mapper;

import com.api.agroguard.exception.ResourceNotFoundException;
import com.api.agroguard.model.UserDO;
import com.api.agroguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserMapper {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // get all users
    @GetMapping("/users/getAll")
    public List<UserDO> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDO loginDetails) {
        UserDO user = userRepository.findByEmail(loginDetails.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loginDetails.getEmail()));

        if (passwordEncoder.matches(loginDetails.getPassword(), user.getPassword())) {
            return ResponseEntity.ok().body("User authenticated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // create user rest API
    @PostMapping("/users/register")
    public ResponseEntity<UserDO> createUser(@RequestBody UserDO user) {
        SecureRandom random = new SecureRandom();
        user.setId(user.getEmail() + "@" + random.nextInt(1000));
        UserDO newUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
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

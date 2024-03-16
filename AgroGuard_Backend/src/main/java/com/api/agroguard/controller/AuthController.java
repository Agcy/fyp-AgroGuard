package com.api.agroguard.controller;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.api.agroguard.entity.*;
import com.api.agroguard.model.UserDO;
import com.api.agroguard.repository.RoleRepository;
import com.api.agroguard.repository.UserRepository;
import com.api.agroguard.service.EmailService;
import com.api.agroguard.service.impl.UserDetailsImpl;
import com.api.agroguard.utils.JwtUtils;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByName(signUpRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserDO user = new UserDO(signUpRequest.getName(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setGender(signUpRequest.getGender());
        user.setOccupation(signUpRequest.getOccupation());
        user.setGmtCreated(LocalDateTime.now());
        user.setGmtModified(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email) {
        // 根据邮箱查找用户
        Optional<UserDO> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email address not found."));
        }
        UserDO user = userOptional.get();
        // 生成重置令牌
        String resetToken = jwtUtils.generatePasswordResetToken(user.getId());
        user.setResetToken(resetToken);
        userRepository.save(user);
        // 发送重置链接到邮箱
        String resetLink = "http://localhost:3000/reset-password?token=" + resetToken;
        // 在生成重置令牌后
        emailService.sendSimpleMessage(
                user.getEmail(),
                "Password Reset Request",
                "To reset your password, click the link below:\n" + resetLink
        );
        // 调用你的邮件服务发送重置链接
        return ResponseEntity.ok(new MessageResponse("Password reset link has been sent to your email."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token, @RequestBody NewPasswordRequest newPasswordRequest) {
        // 验证重置令牌
        Optional<UserDO> userOptional = userRepository.findByResetToken(token);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid or expired password reset token."));
        }
        UserDO user = userOptional.get();
        // 更新用户密码
        user.setPassword(encoder.encode(newPasswordRequest.getNewPassword()));
        user.setResetToken(null); // 清除令牌，确保一次性使用
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Your password has been successfully reset."));
    }

}

package com.anurag.ai.controller;

import com.anurag.ai.dto.LoginRequest;
import com.anurag.ai.dto.SignupRequest;
import com.anurag.ai.dto.UserResponse;
import com.anurag.ai.entity.User;
import com.anurag.ai.service.Jwtservice;
import com.anurag.ai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final Jwtservice jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignupRequest request) {
        User user = userService.signup(request.getName(), request.getEmail(), request.getPassword(), request.getRole());
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(UserResponse.from(user, token));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(UserResponse.from(user, token));
    }
}

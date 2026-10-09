package com.anurag.ai.controller;

import com.anurag.ai.dto.StreakResponse;
import com.anurag.ai.entity.User;
import com.anurag.ai.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequestMapping("/api/streak")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @GetMapping
    public ResponseEntity<StreakResponse> getStreak(@AuthenticationPrincipal User student) {
        return ResponseEntity.ok(streakService.getStreak(student.getId()));
    }
}

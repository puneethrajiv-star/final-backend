package com.anurag.ai.controller;

import com.anurag.ai.dto.OnboardingRequest;
import com.anurag.ai.dto.OnboardingResponse;
import com.anurag.ai.entity.User;
import com.anurag.ai.service.OnboardingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;

    @GetMapping("/questions")
    public ResponseEntity<List<Map<String, Object>>> getQuestions() {
        return ResponseEntity.ok(onboardingService.getQuestions());
    }

    @PostMapping("/submit")
    public ResponseEntity<OnboardingResponse> submitOnboarding(
            @RequestBody(required = false) OnboardingRequest request,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(onboardingService.submitOnboarding(student.getId(), request));
    }
}

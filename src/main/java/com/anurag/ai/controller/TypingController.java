package com.anurag.ai.controller;

import com.anurag.ai.dto.TypingAttemptRequest;
import com.anurag.ai.dto.TypingAttemptResponse;
import com.anurag.ai.entity.User;
import com.anurag.ai.service.TypingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequestMapping("/api/typing")
@RequiredArgsConstructor
public class TypingController {

    private final TypingService typingService;

    @PostMapping("/attempts")
    public ResponseEntity<TypingAttemptResponse> submitAttempt(
            @RequestBody TypingAttemptRequest request,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(typingService.submitAttempt(student.getId(), request));
    }

    @GetMapping("/personal-best")
    public ResponseEntity<TypingAttemptResponse> getPersonalBest(
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(typingService.getPersonalBest(student.getId()));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<TypingAttemptResponse>> getLeaderboard(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(typingService.getLeaderboard(limit));
    }

    @GetMapping("/passages")
    public ResponseEntity<List<com.anurag.ai.dto.TypingPassageResponse>> getPassages(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer level) {
        return ResponseEntity.ok(typingService.getPassages(category, level));
    }

    @GetMapping("/passages/{id}")
    public ResponseEntity<com.anurag.ai.dto.TypingPassageResponse> getPassageById(@PathVariable Long id) {
        return ResponseEntity.ok(typingService.getPassageById(id));
    }

    @GetMapping("/passages/random")
    public ResponseEntity<com.anurag.ai.dto.TypingPassageResponse> getRandomPassage(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer level) {
        return ResponseEntity.ok(typingService.getRandomPassage(category, level));
    }

    @PostMapping("/custom")
    public ResponseEntity<com.anurag.ai.dto.CustomTypingResponse> practiceCustomText(
            @RequestBody com.anurag.ai.dto.CustomTypingRequest request) {
        return ResponseEntity.ok(typingService.processCustomText(request));
    }
}

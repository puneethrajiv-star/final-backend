package com.anurag.ai.controller;

import com.anurag.ai.dto.DsaAttemptRequest;
import com.anurag.ai.dto.DsaAttemptResponse;
import com.anurag.ai.dto.DsaLeaderboardResponse;
import com.anurag.ai.dto.DsaProblemResponse;
import com.anurag.ai.entity.User;
import com.anurag.ai.enums.Difficulty;
import com.anurag.ai.service.DsaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequestMapping("/api/dsa")
@RequiredArgsConstructor
public class DsaController {

    private final DsaService dsaService;

    @GetMapping("/problems")
    public ResponseEntity<List<DsaProblemResponse>> getProblems(
            @RequestParam(required = false) Difficulty difficulty) {
        return ResponseEntity.ok(dsaService.getProblems(difficulty));
    }

    @GetMapping("/problems/{id}")
    public ResponseEntity<DsaProblemResponse> getProblemById(@PathVariable Long id) {
        return ResponseEntity.ok(dsaService.getProblemById(id));
    }

    @PostMapping("/attempts")
    public ResponseEntity<DsaAttemptResponse> submitAttempt(
            @RequestBody DsaAttemptRequest request,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(dsaService.submitAttempt(student.getId(), request));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<DsaLeaderboardResponse>> getLeaderboard() {
        return ResponseEntity.ok(dsaService.getLeaderboard());
    }
}

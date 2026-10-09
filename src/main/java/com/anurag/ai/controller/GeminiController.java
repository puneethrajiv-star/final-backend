package com.anurag.ai.controller;

import com.anurag.ai.dto.AskGeminiRequest;
import com.anurag.ai.dto.AskGeminiResponse;
import com.anurag.ai.entity.Video;
import com.anurag.ai.repository.VideoRepository;
import com.anurag.ai.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;
    private final VideoRepository videoRepository;

    @PostMapping("/ask")
    public ResponseEntity<AskGeminiResponse> ask(@RequestBody AskGeminiRequest request) {
        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));
        String answer = geminiService.ask(video.getUrl(), request.getQuestion());
        return ResponseEntity.ok(new AskGeminiResponse(answer));
    }
}

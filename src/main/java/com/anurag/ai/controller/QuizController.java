package com.anurag.ai.controller;

import com.anurag.ai.dto.VideoQuestionResponse;
import com.anurag.ai.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://frontend-t290.onrender.com")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping({"/api/videos/{videoId}/quiz", "/api/quiz/{videoId}"})
    public ResponseEntity<List<VideoQuestionResponse>> getQuiz(@PathVariable Long videoId) {
        return ResponseEntity.ok(quizService.getQuizByVideoId(videoId));
    }
}

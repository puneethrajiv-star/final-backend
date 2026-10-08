package com.anurag.ai.service;

import com.anurag.ai.dto.TypingAttemptRequest;
import com.anurag.ai.dto.TypingAttemptResponse;
import com.anurag.ai.entity.TypingAttempt;
import com.anurag.ai.entity.User;
import com.anurag.ai.repository.TypingAttemptRepository;
import com.anurag.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.anurag.ai.dto.CustomTypingRequest;
import com.anurag.ai.dto.CustomTypingResponse;
import com.anurag.ai.dto.TypingPassageResponse;
import com.anurag.ai.entity.TypingPassage;
import com.anurag.ai.repository.TypingPassageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TypingService {

    private final TypingAttemptRepository typingAttemptRepository;
    private final TypingPassageRepository typingPassageRepository;
    private final UserRepository userRepository;

    public TypingAttemptResponse submitAttempt(Long studentId, TypingAttemptRequest request) {
        if (request.getWpm() == null || request.getWpm() < 0) {
            throw new IllegalArgumentException("WPM must be non-negative");
        }
        if (request.getAccuracy() == null || request.getAccuracy() < 0 || request.getAccuracy() > 100) {
            throw new IllegalArgumentException("Accuracy must be between 0 and 100");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        TypingAttempt attempt = new TypingAttempt();
        attempt.setStudent(student);
        attempt.setWpm(request.getWpm());
        attempt.setAccuracy(request.getAccuracy());
        attempt.setAttemptedAt(LocalDateTime.now());

        return TypingAttemptResponse.from(typingAttemptRepository.save(attempt));
    }

    public TypingAttemptResponse getPersonalBest(Long studentId) {
        return typingAttemptRepository.findFirstByStudent_IdOrderByWpmDesc(studentId)
                .map(TypingAttemptResponse::from)
                .orElse(null);
    }

    public List<TypingAttemptResponse> getLeaderboard(int limit) {
        int validatedLimit = Math.max(1, Math.min(limit, 100));
        return typingAttemptRepository.findTopAttempts(PageRequest.of(0, validatedLimit)).stream()
                .map(TypingAttemptResponse::from)
                .toList();
    }

    public List<TypingPassageResponse> getPassages(String category, Integer level) {
        List<TypingPassage> passages;
        if (category != null && level != null) {
            passages = typingPassageRepository.findByCategoryIgnoreCaseAndLevel(category, level);
        } else if (category != null) {
            passages = typingPassageRepository.findByCategoryIgnoreCase(category);
        } else {
            passages = typingPassageRepository.findAll();
        }
        return passages.stream().map(TypingPassageResponse::from).toList();
    }

    public TypingPassageResponse getPassageById(Long id) {
        TypingPassage passage = typingPassageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Typing passage not found with id: " + id));
        return TypingPassageResponse.from(passage);
    }

    public TypingPassageResponse getRandomPassage(String category, Integer level) {
        List<TypingPassageResponse> passages = getPassages(category, level);
        if (passages.isEmpty()) {
            throw new IllegalArgumentException("No passages found for specified category and level");
        }
        int index = ThreadLocalRandom.current().nextInt(passages.size());
        return passages.get(index);
    }

    public CustomTypingResponse processCustomText(CustomTypingRequest request) {
        if (request == null || request.getText() == null || request.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Text must not be empty");
        }
        if (request.getText().length() > 1000) {
            throw new IllegalArgumentException("Text exceeds maximum allowed limit of 1000 characters");
        }
        return CustomTypingResponse.from(request.getText().trim());
    }
}

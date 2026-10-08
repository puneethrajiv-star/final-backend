package com.anurag.ai.dto;

import com.anurag.ai.entity.TypingAttempt;
import lombok.Value;
import java.time.LocalDateTime;

@Value
public class TypingAttemptResponse {
    Long id;
    Long studentId;
    String studentName;
    Integer wpm;
    Double accuracy;
    LocalDateTime attemptedAt;

    public static TypingAttemptResponse from(TypingAttempt attempt) {
        return new TypingAttemptResponse(
            attempt.getId(),
            attempt.getStudent() != null ? attempt.getStudent().getId() : null,
            attempt.getStudent() != null ? attempt.getStudent().getName() : null,
            attempt.getWpm(),
            attempt.getAccuracy(),
            attempt.getAttemptedAt()
        );
    }
}

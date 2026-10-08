package com.anurag.ai.dto;

import com.anurag.ai.entity.DsaAttempt;
import lombok.Value;
import java.time.LocalDateTime;

@Value
public class DsaAttemptResponse {
    Long id;
    Long studentId;
    String studentName;
    Long problemId;
    String problemTitle;
    Boolean passed;
    LocalDateTime attemptedAt;

    public static DsaAttemptResponse from(DsaAttempt attempt) {
        return new DsaAttemptResponse(
            attempt.getId(),
            attempt.getStudent() != null ? attempt.getStudent().getId() : null,
            attempt.getStudent() != null ? attempt.getStudent().getName() : null,
            attempt.getProblem() != null ? attempt.getProblem().getId() : null,
            attempt.getProblem() != null ? attempt.getProblem().getTitle() : null,
            attempt.getPassed(),
            attempt.getAttemptedAt()
        );
    }
}

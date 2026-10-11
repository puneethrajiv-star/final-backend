package com.anurag.ai.dto;

import com.anurag.ai.entity.DsaProblem;
import com.anurag.ai.enums.Difficulty;
import lombok.Value;

@Value
public class DsaProblemResponse {
    Long id;
    String title;
    Difficulty difficulty;
    String description;
    String starterCode;
    boolean solved;

    public static DsaProblemResponse from(DsaProblem problem) {
        return from(problem, false);
    }

    public static DsaProblemResponse from(DsaProblem problem, boolean solved) {
        return new DsaProblemResponse(
            problem.getId(),
            problem.getTitle(),
            problem.getDifficulty(),
            problem.getDescription(),
            problem.getStarterCode(),
            solved
        );
    }
}

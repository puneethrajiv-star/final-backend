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

    public static DsaProblemResponse from(DsaProblem problem) {
        return new DsaProblemResponse(
            problem.getId(),
            problem.getTitle(),
            problem.getDifficulty(),
            problem.getDescription(),
            problem.getStarterCode()
        );
    }
}

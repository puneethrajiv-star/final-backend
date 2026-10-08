package com.anurag.ai.dto;

import lombok.Data;

@Data
public class OnboardingRequest {
    private String intermediateStream;
    private String codingExperience;
    private Integer mathComfort;
    private String primaryGoal;
    private Long preferredCourseId;
}

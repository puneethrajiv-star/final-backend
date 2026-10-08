package com.anurag.ai.dto;

import lombok.Value;

@Value
public class OnboardingResponse {
    Long studentId;
    String studentName;
    CourseResponse recommendedCourse;
    String enrollmentStage;
    String message;
}

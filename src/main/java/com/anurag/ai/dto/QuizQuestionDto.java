package com.anurag.ai.dto;

import lombok.Data;

@Data
public class QuizQuestionDto {
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;
}

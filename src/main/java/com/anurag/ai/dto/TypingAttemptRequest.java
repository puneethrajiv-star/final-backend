package com.anurag.ai.dto;

import lombok.Data;

@Data
public class TypingAttemptRequest {
    private Integer wpm;
    private Double accuracy;
}

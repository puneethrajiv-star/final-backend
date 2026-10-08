package com.anurag.ai.dto;

import lombok.Data;

@Data
public class AskGeminiRequest {
    private Long videoId;
    private String question;
}

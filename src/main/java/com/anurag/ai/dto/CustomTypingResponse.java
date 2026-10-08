package com.anurag.ai.dto;

import lombok.Value;

@Value
public class CustomTypingResponse {
    String text;
    int characterCount;
    int wordCount;

    public static CustomTypingResponse from(String text) {
        int length = text != null ? text.length() : 0;
        int words = text == null || text.isBlank() ? 0 : text.trim().split("\\s+").length;
        return new CustomTypingResponse(text, length, words);
    }
}

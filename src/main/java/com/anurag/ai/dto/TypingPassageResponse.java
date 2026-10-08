package com.anurag.ai.dto;

import com.anurag.ai.entity.TypingPassage;
import lombok.Value;

@Value
public class TypingPassageResponse {
    Long id;
    String category;
    Integer level;
    String content;
    String source;
    int wordCount;

    public static TypingPassageResponse from(TypingPassage passage) {
        int words = passage.getContent() == null || passage.getContent().isBlank() ? 0
                : passage.getContent().trim().split("\\s+").length;
        return new TypingPassageResponse(
            passage.getId(),
            passage.getCategory(),
            passage.getLevel(),
            passage.getContent(),
            passage.getSource(),
            words
        );
    }
}

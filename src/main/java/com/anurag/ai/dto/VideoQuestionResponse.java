package com.anurag.ai.dto;

import com.anurag.ai.entity.VideoQuestion;
import lombok.Value;

@Value
public class VideoQuestionResponse {
    Long id;
    Long videoId;
    String questionText;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctOption;

    public static VideoQuestionResponse from(VideoQuestion vq) {
        return new VideoQuestionResponse(
            vq.getId(),
            vq.getVideo() != null ? vq.getVideo().getId() : null,
            vq.getQuestionText(),
            vq.getOptionA(),
            vq.getOptionB(),
            vq.getOptionC(),
            vq.getOptionD(),
            vq.getCorrectOption()
        );
    }
}

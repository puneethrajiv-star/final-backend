package com.anurag.ai.dto;

import com.anurag.ai.entity.Video;
import lombok.Value;

@Value
public class VideoResponse {
    Long id;
    String title;
    String url;
    Long courseId;

    public static VideoResponse from(Video video) {
        return new VideoResponse(
            video.getId(),
            video.getTitle(),
            video.getUrl(),
            video.getCourse() != null ? video.getCourse().getId() : null
        );
    }
}

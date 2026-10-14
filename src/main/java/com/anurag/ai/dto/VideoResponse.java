package com.anurag.ai.dto;

import com.anurag.ai.entity.Video;
import lombok.Value;

@Value
public class VideoResponse {
    Long id;
    String title;
    String url;
    String module;
    Integer orderIndex;
    Long courseId;

    public static VideoResponse from(Video video) {
        return new VideoResponse(
            video.getId(),
            video.getTitle(),
            video.getUrl(),
            video.getModule(),
            video.getOrderIndex(),
            video.getCourse() != null ? video.getCourse().getId() : null
        );
    }
}

package com.anurag.ai.dto;

import lombok.Value;

import java.util.List;

@Value
public class CourseProgressResponse {
    Long courseId;
    long totalVideos;
    long watchedVideos;
    int progressPercentage;
    List<Long> watchedVideoIds;
}

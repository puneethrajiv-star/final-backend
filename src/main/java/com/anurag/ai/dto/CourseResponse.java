package com.anurag.ai.dto;

import com.anurag.ai.entity.Course;
import lombok.Value;

@Value
public class CourseResponse {
    Long id;
    String title;
    String description;
    String createdByName;
    String visibility;

    public static CourseResponse from(Course course) {
        return new CourseResponse(
            course.getId(),
            course.getTitle(),
            course.getDescription(),
            course.getCreatedBy() != null ? course.getCreatedBy().getName() : null,
            course.getVisibility() != null ? course.getVisibility().name() : null
        );
    }
}

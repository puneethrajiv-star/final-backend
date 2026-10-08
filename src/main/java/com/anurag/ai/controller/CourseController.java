package com.anurag.ai.controller;

import com.anurag.ai.dto.CourseResponse;
import com.anurag.ai.dto.VideoResponse;
import com.anurag.ai.entity.User;
import com.anurag.ai.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/enrolled")
    public ResponseEntity<List<CourseResponse>> getEnrolledCourses(@AuthenticationPrincipal User student) {
        return ResponseEntity.ok(courseService.getEnrolledCourses(student.getId()));
    }

    @GetMapping("/{courseId}/videos")
    public ResponseEntity<List<VideoResponse>> getCourseVideos(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(courseService.getCourseVideos(student.getId(), courseId));
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<CourseResponse> enroll(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(courseService.enrollStudent(student.getId(), courseId));
    }

    @PostMapping("/{courseId}/videos/{videoId}/progress")
    public ResponseEntity<com.anurag.ai.dto.CourseProgressResponse> markVideoProgress(
            @PathVariable Long courseId,
            @PathVariable Long videoId,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(courseService.markVideoProgress(student.getId(), courseId, videoId));
    }

    @GetMapping("/{courseId}/progress")
    public ResponseEntity<com.anurag.ai.dto.CourseProgressResponse> getCourseProgress(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User student) {
        return ResponseEntity.ok(courseService.getCourseProgress(student.getId(), courseId));
    }
}

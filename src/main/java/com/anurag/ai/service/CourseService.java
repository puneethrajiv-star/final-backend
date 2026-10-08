package com.anurag.ai.service;

import com.anurag.ai.dto.CourseResponse;
import com.anurag.ai.dto.VideoResponse;
import com.anurag.ai.entity.Course;
import com.anurag.ai.entity.Enrollment;
import com.anurag.ai.entity.User;
import com.anurag.ai.repository.CourseRepository;
import com.anurag.ai.repository.EnrollmentRepository;
import com.anurag.ai.repository.UserRepository;
import com.anurag.ai.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.anurag.ai.dto.CourseProgressResponse;
import com.anurag.ai.entity.Video;
import com.anurag.ai.entity.VideoProgress;
import com.anurag.ai.repository.VideoProgressRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final VideoRepository videoRepository;
    private final VideoProgressRepository videoProgressRepository;
    private final UserRepository userRepository;

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseResponse::from)
                .toList();
    }

    public List<CourseResponse> getEnrolledCourses(Long studentId) {
        return enrollmentRepository.findByStudent_Id(studentId).stream()
                .map(Enrollment::getCourse)
                .map(CourseResponse::from)
                .toList();
    }

    public List<VideoResponse> getCourseVideos(Long studentId, Long courseId) {
        boolean enrolled = enrollmentRepository.findByStudent_IdAndCourse_Id(studentId, courseId).isPresent();
        if (!enrolled) {
            throw new IllegalArgumentException("Student is not enrolled in this course");
        }
        return videoRepository.findByCourse_Id(courseId).stream()
                .map(VideoResponse::from)
                .toList();
    }

    public CourseResponse enrollStudent(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Enrollment enrollment = enrollmentRepository.findByStudent_IdAndCourse_Id(studentId, courseId)
                .orElseGet(() -> {
                    Enrollment e = new Enrollment();
                    e.setStudent(student);
                    e.setCourse(course);
                    e.setCurrentStage("START");
                    e.setTimeSpentSeconds(0);
                    e.setLastAccessedAt(LocalDateTime.now());
                    return enrollmentRepository.save(e);
                });

        return CourseResponse.from(enrollment.getCourse());
    }

    public CourseProgressResponse markVideoProgress(Long studentId, Long courseId, Long videoId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found"));

        if (!video.getCourse().getId().equals(courseId)) {
            throw new IllegalArgumentException("Video does not belong to course");
        }

        VideoProgress progress = videoProgressRepository.findByStudent_IdAndVideo_Id(studentId, videoId)
                .orElseGet(() -> {
                    VideoProgress vp = new VideoProgress();
                    vp.setStudent(student);
                    vp.setVideo(video);
                    return vp;
                });
        progress.setWatched(true);
        progress.setWatchedAt(LocalDateTime.now());
        videoProgressRepository.save(progress);

        return getCourseProgress(studentId, courseId);
    }

    public CourseProgressResponse getCourseProgress(Long studentId, Long courseId) {
        List<Video> videos = videoRepository.findByCourse_Id(courseId);
        long totalVideos = videos.size();

        List<VideoProgress> progressList = videoProgressRepository.findByStudent_IdAndVideo_Course_Id(studentId, courseId);
        List<Long> watchedIds = progressList.stream()
                .filter(vp -> Boolean.TRUE.equals(vp.getWatched()))
                .map(vp -> vp.getVideo().getId())
                .distinct()
                .toList();

        long watchedVideos = watchedIds.size();
        int percentage = totalVideos > 0 ? (int) Math.round(((double) watchedVideos / totalVideos) * 100) : 0;

        return new CourseProgressResponse(courseId, totalVideos, watchedVideos, percentage, watchedIds);
    }
}

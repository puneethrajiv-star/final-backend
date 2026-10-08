package com.anurag.ai.service;

import com.anurag.ai.dto.CourseResponse;
import com.anurag.ai.dto.OnboardingRequest;
import com.anurag.ai.dto.OnboardingResponse;
import com.anurag.ai.entity.Course;
import com.anurag.ai.entity.Enrollment;
import com.anurag.ai.entity.User;
import com.anurag.ai.repository.CourseRepository;
import com.anurag.ai.repository.EnrollmentRepository;
import com.anurag.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public List<Map<String, Object>> getQuestions() {
        return List.of(
            Map.of(
                "id", "intermediateStream",
                "question", "What was your 11th/12th grade (Intermediate) stream?",
                "options", List.of("MPC (Maths, Physics, Chemistry)", "BiPC (Biology, Physics, Chemistry)", "Commerce/Arts", "Other")
            ),
            Map.of(
                "id", "codingExperience",
                "question", "What is your prior programming experience?",
                "options", List.of("None (Complete Beginner)", "Basic (Syntax/Variables)", "Moderate (Written small projects)")
            ),
            Map.of(
                "id", "mathComfort",
                "question", "How comfortable are you with high school mathematics?",
                "options", List.of("1 - Need refreshers", "2 - Average", "3 - Comfortable", "4 - Very confident")
            ),
            Map.of(
                "id", "primaryGoal",
                "question", "What is your main goal before your BTech 1st semester starts?",
                "options", List.of("Bridge from Intermediate to CS", "Learn DSA early", "Build coding speed & confidence")
            )
        );
    }

    @Transactional
    public OnboardingResponse submitOnboarding(Long studentId, OnboardingRequest request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Course course;
        if (request != null && request.getPreferredCourseId() != null) {
            course = courseRepository.findById(request.getPreferredCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Preferred course not found"));
        } else {
            List<Course> allCourses = courseRepository.findAll();
            if (allCourses.isEmpty()) {
                Course defaultCourse = new Course();
                defaultCourse.setTitle("Transition to BTech CS: Foundations");
                defaultCourse.setDescription("Core foundation course covering logic, algorithms, and practical programming for 1st year BTech CS students.");
                course = courseRepository.save(defaultCourse);
            } else {
                course = allCourses.get(0);
            }
        }

        final Course recommendedCourse = course;
        Enrollment enrollment = enrollmentRepository.findByStudent_IdAndCourse_Id(studentId, recommendedCourse.getId())
                .orElseGet(() -> {
                    Enrollment e = new Enrollment();
                    e.setStudent(student);
                    e.setCourse(recommendedCourse);
                    e.setCurrentStage("ONBOARDING_COMPLETED");
                    e.setTimeSpentSeconds(0);
                    e.setLastAccessedAt(LocalDateTime.now());
                    return enrollmentRepository.save(e);
                });

        return new OnboardingResponse(
                student.getId(),
                student.getName(),
                CourseResponse.from(course),
                enrollment.getCurrentStage(),
                "Onboarding successful. Enrolled in recommended course: " + course.getTitle()
        );
    }
}

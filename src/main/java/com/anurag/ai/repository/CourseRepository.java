package com.anurag.ai.repository;

import com.anurag.ai.entity.Course;
import com.anurag.ai.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByVisibility(Visibility visibility);
    List<Course> findByCreatedById(Long userId);
}

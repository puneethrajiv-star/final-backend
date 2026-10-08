package com.anurag.ai.repository;

import com.anurag.ai.entity.Enrollment;
import com.anurag.ai.entity.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    List<Enrollment> findByStudent_Id(Long studentId);
    Optional<Enrollment> findByStudent_IdAndCourse_Id(Long studentId, Long courseId);
}

package com.anurag.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@IdClass(EnrollmentId.class)
@Getter @Setter
public class Enrollment {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "current_stage")
    private String currentStage;

    @Column(name = "time_spent_seconds", nullable = false)
    private Integer timeSpentSeconds = 0;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;
}

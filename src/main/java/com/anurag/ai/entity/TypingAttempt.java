package com.anurag.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "typing_attempts")
@Getter
@Setter
public class TypingAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private Integer wpm;

    @Column(nullable = false)
    private Double accuracy;

    @Column(name = "attempted_at", nullable = false)
    private LocalDateTime attemptedAt;
}

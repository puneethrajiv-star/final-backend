package com.anurag.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_progress")
@IdClass(VideoProgressId.class)
@Getter @Setter
public class VideoProgress {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @Column(nullable = false)
    private Boolean watched = false;

    private LocalDateTime watchedAt;
}

package com.anurag.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "videos")
@Getter
@Setter
public class Video {
    @Column(columnDefinition = "TEXT")
    private String transcript;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    // Chapter grouping shown in the syllabus sidebar, e.g. "Loops", "Functions".
    private String module;

    // Stable playlist order; falls back to id order when null.
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}

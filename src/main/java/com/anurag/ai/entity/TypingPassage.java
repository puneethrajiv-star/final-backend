package com.anurag.ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "typing_passages")
@Getter
@Setter
public class TypingPassage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String category; // 'TEXT' or 'CODE'

    @Column(nullable = false)
    private Integer level;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String source;
}

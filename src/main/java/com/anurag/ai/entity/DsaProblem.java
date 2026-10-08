package com.anurag.ai.entity;

import com.anurag.ai.enums.Difficulty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dsa_problems")
@Getter
@Setter
public class DsaProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "starter_code", columnDefinition = "TEXT")
    private String starterCode;
}

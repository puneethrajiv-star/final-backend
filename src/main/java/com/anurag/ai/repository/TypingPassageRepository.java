package com.anurag.ai.repository;

import com.anurag.ai.entity.TypingPassage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TypingPassageRepository extends JpaRepository<TypingPassage, Long> {
    List<TypingPassage> findByCategoryIgnoreCase(String category);
    List<TypingPassage> findByCategoryIgnoreCaseAndLevel(String category, Integer level);
}

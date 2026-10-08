package com.anurag.ai.repository;

import com.anurag.ai.entity.DsaProblem;
import com.anurag.ai.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DsaProblemRepository extends JpaRepository<DsaProblem, Long> {
    List<DsaProblem> findByDifficulty(Difficulty difficulty);
    boolean existsByTitle(String title);
}

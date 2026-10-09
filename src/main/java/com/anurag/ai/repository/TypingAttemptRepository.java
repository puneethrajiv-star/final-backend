package com.anurag.ai.repository;

import com.anurag.ai.entity.TypingAttempt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TypingAttemptRepository extends JpaRepository<TypingAttempt, Long> {

    Optional<TypingAttempt> findFirstByStudent_IdOrderByWpmDesc(Long studentId);

    @Query("SELECT t FROM TypingAttempt t JOIN FETCH t.student ORDER BY t.wpm DESC, t.accuracy DESC")
    List<TypingAttempt> findTopAttempts(Pageable pageable);

    @Query("SELECT DISTINCT CAST(t.attemptedAt AS LocalDate) FROM TypingAttempt t WHERE t.student.id = :studentId")
    List<LocalDate> findDistinctActivityDates(Long studentId);
}

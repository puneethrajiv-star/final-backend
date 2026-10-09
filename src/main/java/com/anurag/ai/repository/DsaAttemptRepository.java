package com.anurag.ai.repository;

import com.anurag.ai.entity.DsaAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DsaAttemptRepository extends JpaRepository<DsaAttempt, Long> {

    @Query("SELECT a.student.id AS studentId, a.student.name AS studentName, COUNT(DISTINCT a.problem.id) AS passedCount " +
           "FROM DsaAttempt a WHERE a.passed = true " +
           "GROUP BY a.student.id, a.student.name " +
           "ORDER BY passedCount DESC")
    List<DsaLeaderboardRow> getLeaderboard();

    @Query("SELECT DISTINCT CAST(a.attemptedAt AS LocalDate) FROM DsaAttempt a WHERE a.student.id = :studentId")
    List<LocalDate> findDistinctActivityDates(Long studentId);

    interface DsaLeaderboardRow {
        Long getStudentId();
        String getStudentName();
        Long getPassedCount();
    }
}

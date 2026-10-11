package com.anurag.ai.service;

import com.anurag.ai.dto.DsaAttemptRequest;
import com.anurag.ai.dto.DsaAttemptResponse;
import com.anurag.ai.dto.DsaLeaderboardResponse;
import com.anurag.ai.dto.DsaProblemResponse;
import com.anurag.ai.entity.DsaAttempt;
import com.anurag.ai.entity.DsaProblem;
import com.anurag.ai.entity.User;
import com.anurag.ai.enums.Difficulty;
import com.anurag.ai.repository.DsaAttemptRepository;
import com.anurag.ai.repository.DsaProblemRepository;
import com.anurag.ai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DsaService {

    private final DsaProblemRepository dsaProblemRepository;
    private final DsaAttemptRepository dsaAttemptRepository;
    private final UserRepository userRepository;

    public List<DsaProblemResponse> getProblems(Long studentId, Difficulty difficulty) {
        List<DsaProblem> problems;
        if (difficulty != null) {
            problems = dsaProblemRepository.findByDifficulty(difficulty);
        } else {
            problems = dsaProblemRepository.findAll();
        }

        Set<Long> solvedIds = studentId != null
                ? new HashSet<>(dsaAttemptRepository.findSolvedProblemIds(studentId))
                : Set.of();

        return problems.stream()
                .map(p -> DsaProblemResponse.from(p, solvedIds.contains(p.getId())))
                .toList();
    }

    public DsaProblemResponse getProblemById(Long studentId, Long id) {
        DsaProblem problem = dsaProblemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("DSA problem not found with id: " + id));
        boolean solved = studentId != null
                && dsaAttemptRepository.findSolvedProblemIds(studentId).contains(id);
        return DsaProblemResponse.from(problem, solved);
    }

    public DsaAttemptResponse submitAttempt(Long studentId, DsaAttemptRequest request) {
        if (request.getProblemId() == null) {
            throw new IllegalArgumentException("Problem ID must not be null");
        }
        if (request.getPassed() == null) {
            throw new IllegalArgumentException("Passed status must not be null");
        }

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        DsaProblem problem = dsaProblemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException("DSA problem not found"));

        DsaAttempt attempt = new DsaAttempt();
        attempt.setStudent(student);
        attempt.setProblem(problem);
        attempt.setPassed(request.getPassed());
        attempt.setAttemptedAt(LocalDateTime.now());

        return DsaAttemptResponse.from(dsaAttemptRepository.save(attempt));
    }

    public List<DsaLeaderboardResponse> getLeaderboard() {
        return dsaAttemptRepository.getLeaderboard().stream()
                .map(row -> new DsaLeaderboardResponse(row.getStudentId(), row.getStudentName(), row.getPassedCount()))
                .toList();
    }
}

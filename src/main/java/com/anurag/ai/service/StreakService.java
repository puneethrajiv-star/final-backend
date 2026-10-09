package com.anurag.ai.service;

import com.anurag.ai.dto.StreakResponse;
import com.anurag.ai.repository.DsaAttemptRepository;
import com.anurag.ai.repository.TypingAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final TypingAttemptRepository typingAttemptRepository;
    private final DsaAttemptRepository dsaAttemptRepository;

    public StreakResponse getStreak(Long studentId) {
        Set<LocalDate> activeDays = new HashSet<>();
        activeDays.addAll(typingAttemptRepository.findDistinctActivityDates(studentId));
        activeDays.addAll(dsaAttemptRepository.findDistinctActivityDates(studentId));

        LocalDate today = LocalDate.now();

        // Current streak: count back from today (or yesterday, if nothing done yet today)
        int streak = 0;
        LocalDate cursor = activeDays.contains(today) ? today : today.minusDays(1);
        while (activeDays.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }

        // Last 7 days, oldest first, for the week-row UI
        List<Boolean> last7Days = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            last7Days.add(activeDays.contains(today.minusDays(i)));
        }

        return new StreakResponse(streak, last7Days);
    }
}

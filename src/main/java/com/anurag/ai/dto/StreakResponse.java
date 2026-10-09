package com.anurag.ai.dto;

import lombok.Value;
import java.util.List;

@Value
public class StreakResponse {
    int currentStreak;
    List<Boolean> last7Days; // index 0 = 6 days ago ... index 6 = today
}

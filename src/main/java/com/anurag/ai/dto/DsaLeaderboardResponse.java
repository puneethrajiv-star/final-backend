package com.anurag.ai.dto;

import lombok.Value;

@Value
public class DsaLeaderboardResponse {
    Long studentId;
    String studentName;
    Long passedCount;
}

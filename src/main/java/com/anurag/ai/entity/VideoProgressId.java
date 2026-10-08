package com.anurag.ai.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class VideoProgressId implements Serializable {
    private User student;
    private Video video;
}

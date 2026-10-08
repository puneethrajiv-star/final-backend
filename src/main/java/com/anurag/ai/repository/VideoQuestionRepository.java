package com.anurag.ai.repository;

import com.anurag.ai.entity.VideoQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VideoQuestionRepository extends JpaRepository<VideoQuestion, Long> {
    List<VideoQuestion> findByVideo_Id(Long videoId);
}

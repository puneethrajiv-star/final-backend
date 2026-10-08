package com.anurag.ai.repository;

import com.anurag.ai.entity.VideoProgress;
import com.anurag.ai.entity.VideoProgressId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VideoProgressRepository extends JpaRepository<VideoProgress, VideoProgressId> {
    Optional<VideoProgress> findByStudent_IdAndVideo_Id(Long studentId, Long videoId);
    List<VideoProgress> findByStudent_Id(Long studentId);
    List<VideoProgress> findByStudent_IdAndVideo_Course_Id(Long studentId, Long courseId);
}

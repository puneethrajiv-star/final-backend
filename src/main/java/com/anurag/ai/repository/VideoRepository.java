package com.anurag.ai.repository;

import com.anurag.ai.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByCourse_Id(Long courseId);
}

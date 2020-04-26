package com.github.grastasss.task.repository;

import com.github.grastasss.task.data.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllBySpeakerId(Long speakerId);
}

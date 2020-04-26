package com.github.grastasss.task.service;

import com.github.grastasss.task.data.Lecture;
import com.github.grastasss.task.data.Speaker;
import com.github.grastasss.task.payload.CreateLectureRequest;
import com.github.grastasss.task.payload.LectureDTO;
import com.github.grastasss.task.payload.UpdateLectureRequest;
import com.github.grastasss.task.repository.LectureRepository;
import com.github.grastasss.task.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final SpeakerRepository speakerRepository;

    @Transactional(readOnly = true)
    public List<LectureDTO> getBySpeakerId(Long speakerId) {
        return this.lectureRepository.findAllBySpeakerId(speakerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LectureDTO> get(Long id) {
        return this.lectureRepository
                .findById(id)
                .map(this::toDTO);
    }

    @Transactional
    public LectureDTO create(CreateLectureRequest data) {
        if (data.getSpeakerId() == null) throw new IllegalArgumentException();

        final Speaker speaker = this.speakerRepository
                .findById(data.getSpeakerId())
                .orElseThrow(IllegalArgumentException::new);

        final Lecture lecture = Lecture.builder()
                .name(data.getName())
                .date(data.getDate())
                .speaker(speaker)
                .build();

        return toDTO(this.lectureRepository.save(lecture));
    }

    @Transactional
    public void update(Long id, UpdateLectureRequest data) {
        final Lecture lecture = this.lectureRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);

        final Speaker speaker = this.speakerRepository
                .findById(data.getSpeakerId())
                .orElseThrow(IllegalArgumentException::new);

        lecture.setName(data.getName());
        lecture.setDate(data.getDate());
        lecture.setSpeaker(speaker);
    }

    @Transactional
    public void delete(Long id) {
        this.lectureRepository.deleteById(id);
    }

    private LectureDTO toDTO(Lecture lecture) {
        final LectureDTO dto = new LectureDTO();
        dto.setId(lecture.getId());
        dto.setName(lecture.getName());
        dto.setDate(lecture.getDate());
        dto.setSpeakerId(lecture.getSpeaker().getId());
        return dto;
    }
}

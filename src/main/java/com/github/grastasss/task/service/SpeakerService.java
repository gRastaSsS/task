package com.github.grastasss.task.service;

import com.github.grastasss.task.data.Speaker;
import com.github.grastasss.task.payload.CreateSpeakerRequest;
import com.github.grastasss.task.payload.SpeakerDTO;
import com.github.grastasss.task.payload.UpdateSpeakerRequest;
import com.github.grastasss.task.repository.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpeakerService {
    private final SpeakerRepository speakerRepository;

    @Transactional(readOnly = true)
    public Optional<SpeakerDTO> get(Long id) {
        return this.speakerRepository
                .findById(id)
                .map(this::toDTO);
    }

    @Transactional
    public SpeakerDTO create(CreateSpeakerRequest data) {
        final Speaker speaker = Speaker.builder()
                .firstName(data.getFirstName())
                .middleName(data.getMiddleName())
                .lastName(data.getLastName())
                .build();

        return toDTO(this.speakerRepository.save(speaker));
    }

    @Transactional
    public void update(Long id, UpdateSpeakerRequest data) {
        final Speaker speaker = this.speakerRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);

        speaker.setFirstName(data.getFirstName());
        speaker.setMiddleName(data.getMiddleName());
        speaker.setLastName(data.getLastName());
    }

    @Transactional
    public void delete(Long id) {
        this.speakerRepository.deleteById(id);
    }

    private SpeakerDTO toDTO(Speaker speaker) {
        final SpeakerDTO dto = new SpeakerDTO();
        dto.setId(speaker.getId());
        dto.setFirstName(speaker.getFirstName());
        dto.setMiddleName(speaker.getMiddleName());
        dto.setLastName(speaker.getLastName());
        return dto;
    }
}

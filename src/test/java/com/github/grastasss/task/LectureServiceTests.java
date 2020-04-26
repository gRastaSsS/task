package com.github.grastasss.task;

import com.github.grastasss.task.payload.CreateLectureRequest;
import com.github.grastasss.task.payload.CreateSpeakerRequest;
import com.github.grastasss.task.payload.LectureDTO;
import com.github.grastasss.task.payload.UpdateLectureRequest;
import com.github.grastasss.task.service.LectureService;
import com.github.grastasss.task.service.SpeakerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class LectureServiceTests {
    static Long speakerId0, speakerId1, speakerId2;

    @Autowired private LectureService service;

    @BeforeAll
    static void init(@Autowired SpeakerService speakerService) {
        CreateSpeakerRequest request = new CreateSpeakerRequest();
        speakerId0 = speakerService.create(request).getId();
        speakerId1 = speakerService.create(request).getId();
        speakerId2 = speakerService.create(request).getId();
    }

    @Test
    void getShouldReturnEmptyOptional() {
        assertThat(service.get(999L)).isEmpty();
    }

    @Test
    void createWithInvalidSpeakerShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            CreateLectureRequest request = new CreateLectureRequest();
            request.setName("lec_1");
            request.setDate(new Date());
            request.setSpeakerId(777L);
            service.create(request);
        });
    }

    @Test
    void createWithNullSpeakerShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            CreateLectureRequest request = new CreateLectureRequest();
            request.setName("lec_2");
            request.setDate(new Date());
            request.setSpeakerId(null);
            service.create(request);
        });
    }

    @Test
    void createShouldReturn() {
        CreateLectureRequest request = new CreateLectureRequest();
        request.setName("lec_2");
        request.setDate(new Date());
        request.setSpeakerId(speakerId0);
        LectureDTO created = service.create(request);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo(request.getName());
        assertThat(created.getDate()).isEqualTo(request.getDate());
    }

    @Test
    void getShouldReturnAfterCreation() {
        CreateLectureRequest request = new CreateLectureRequest();
        request.setName("lec_3");
        request.setSpeakerId(speakerId0);

        LectureDTO created = service.create(request);
        Optional<LectureDTO> got = service.get(created.getId());

        assertThat(got).isNotEmpty();
        assertThat(got.get().getName()).isEqualTo(request.getName());
    }

    @Test
    void updateShouldThrowExceptionOnNonExistingEntity() {
        assertThrows(NullPointerException.class, () -> {
            UpdateLectureRequest request = new UpdateLectureRequest();
            request.setName("lec_3");
            service.update(555L, request);
        });
    }

    @Test
    void updateShouldUpdateOnExistingEntity() {
        CreateLectureRequest request0 = new CreateLectureRequest();
        request0.setName("lec_4");
        request0.setSpeakerId(speakerId0);
        request0.setDate(new Date());
        Long id = service.create(request0).getId();

        UpdateLectureRequest request1 = new UpdateLectureRequest();
        request1.setName("lec_5");
        request1.setSpeakerId(speakerId1);
        request0.setDate(new Date());
        service.update(id, request1);

        Optional<LectureDTO> data = service.get(id);
        assertThat(data).isNotEmpty();
        assertThat(data.get().getName()).isEqualTo(request1.getName());
        assertThat(data.get().getDate()).isEqualTo(request1.getDate());
        assertThat(data.get().getSpeakerId()).isEqualTo(request1.getSpeakerId());
    }

    @Test
    void deleteShouldDeleteExistingEntity() {
        CreateLectureRequest request0 = new CreateLectureRequest();
        request0.setName("lec_6");
        request0.setSpeakerId(speakerId0);
        request0.setDate(new Date());
        Long id = service.create(request0).getId();
        assertThat(service.get(id)).isNotEmpty();
        service.delete(id);
        assertThat(service.get(id)).isEmpty();
    }

    @Test
    void cascadeDelete(@Autowired SpeakerService speakerService) {
        CreateLectureRequest request0 = new CreateLectureRequest();
        request0.setName("lec_7");
        request0.setSpeakerId(speakerId2);
        Long id = service.create(request0).getId();

        assertThat(service.get(id)).isNotEmpty();
        speakerService.delete(speakerId2);
        assertThat(service.get(id)).isEmpty();
    }
}

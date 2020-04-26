package com.github.grastasss.task;

import com.github.grastasss.task.payload.CreateSpeakerRequest;
import com.github.grastasss.task.payload.SpeakerDTO;
import com.github.grastasss.task.payload.UpdateSpeakerRequest;
import com.github.grastasss.task.service.SpeakerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SpeakerServiceTests {
    @Autowired private SpeakerService service;

    @Test
    void contextLoads() { }

    @Test
    void getShouldReturnEmptyOptional() {
        assertThat(service.get(999L)).isEmpty();
    }

    @Test
    void createShouldReturn() {
        CreateSpeakerRequest request = new CreateSpeakerRequest();
        request.setFirstName("user0_f");
        request.setMiddleName("user0_m");
        request.setLastName("user0_l");
        SpeakerDTO created = service.create(request);
        assertThat(created.getId()).isNotNull();
        assertThat(created.getFirstName()).isEqualTo(request.getFirstName());
        assertThat(created.getMiddleName()).isEqualTo(request.getMiddleName());
        assertThat(created.getLastName()).isEqualTo(request.getLastName());
    }

    @Test
    void getShouldReturnAfterCreation() {
        CreateSpeakerRequest request = new CreateSpeakerRequest();
        request.setFirstName("user1_f");
        request.setMiddleName("user1_m");
        request.setLastName("user1_l");
        SpeakerDTO created = service.create(request);
        Optional<SpeakerDTO> got = service.get(created.getId());
        assertThat(got).isNotEmpty();
        assertThat(got.get().getFirstName()).isEqualTo(request.getFirstName());
    }

    @Test
    void updateShouldThrowExceptionOnNonExistingEntity() {
        assertThrows(NullPointerException.class, () -> {
            UpdateSpeakerRequest request = new UpdateSpeakerRequest();
            request.setFirstName("user1_f");
            request.setMiddleName("user1_m");
            request.setLastName("user1_l");
            service.update(888L, request);
        });
    }

    @Test
    void updateShouldUpdateOnExistingEntity() {
        CreateSpeakerRequest request0 = new CreateSpeakerRequest();
        request0.setFirstName("user2_f");
        request0.setMiddleName("user2_m");
        request0.setLastName("user2_l");
        Long id = service.create(request0).getId();

        UpdateSpeakerRequest request1 = new UpdateSpeakerRequest();
        request1.setFirstName("user3_f");
        request1.setMiddleName("user3_m");
        request1.setLastName("user3_l");
        service.update(id, request1);

        Optional<SpeakerDTO> data = service.get(id);
        assertThat(data).isNotEmpty();
        assertThat(data.get().getFirstName()).isEqualTo(request1.getFirstName());
        assertThat(data.get().getMiddleName()).isEqualTo(request1.getMiddleName());
        assertThat(data.get().getLastName()).isEqualTo(request1.getLastName());
    }

    @Test
    void deleteShouldDeleteExistingEntity() {
        CreateSpeakerRequest request0 = new CreateSpeakerRequest();
        request0.setFirstName("user4_f");
        request0.setMiddleName("user4_m");
        request0.setLastName("user4_l");
        Long id = service.create(request0).getId();
        assertThat(service.get(id)).isNotEmpty();
        service.delete(id);
        assertThat(service.get(id)).isEmpty();
    }
}

package com.github.grastasss.task.controller;

import com.github.grastasss.task.payload.CreateSpeakerRequest;
import com.github.grastasss.task.payload.SpeakerDTO;
import com.github.grastasss.task.payload.UpdateSpeakerRequest;
import com.github.grastasss.task.service.SpeakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speakers")
@RequiredArgsConstructor
public class SpeakerRest {
    private final SpeakerService service;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<SpeakerDTO> get(@PathVariable Long id) {
        return this.service.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    public ResponseEntity<SpeakerDTO> create(@RequestBody CreateSpeakerRequest data) {
        try {
            return ResponseEntity.ok(this.service.create(data));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateSpeakerRequest data) {
        try {
            this.service.update(id, data);
            return ResponseEntity.ok().build();
        } catch (NullPointerException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }
}

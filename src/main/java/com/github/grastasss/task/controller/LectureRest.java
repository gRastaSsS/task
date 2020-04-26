package com.github.grastasss.task.controller;

import com.github.grastasss.task.payload.CreateLectureRequest;
import com.github.grastasss.task.payload.LectureDTO;
import com.github.grastasss.task.payload.UpdateLectureRequest;
import com.github.grastasss.task.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureRest {
    private final LectureService service;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<LectureDTO> get(@PathVariable Long id) {
        return this.service.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/")
    public ResponseEntity<List<LectureDTO>> getBySpeaker(@RequestParam Long speakerId) {
        return ResponseEntity.ok(this.service.getBySpeakerId(speakerId));
    }

    @RequestMapping(method = RequestMethod.POST, path = "/")
    public ResponseEntity<LectureDTO> create(@RequestBody CreateLectureRequest data) {
        try {
            return ResponseEntity.ok(this.service.create(data));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateLectureRequest data) {
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

package com.github.grastasss.task.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class CreateLectureRequest {
    private String name;
    private Date date;
    private Long speakerId;
}

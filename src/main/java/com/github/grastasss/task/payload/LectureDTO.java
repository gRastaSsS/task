package com.github.grastasss.task.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class LectureDTO {
    private Long id;
    private String name;
    private Date date;
    private Long speakerId;
}

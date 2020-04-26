package com.github.grastasss.task.payload;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateSpeakerRequest {
    private String firstName;
    private String middleName;
    private String lastName;
}

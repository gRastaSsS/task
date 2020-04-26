package com.github.grastasss.task.data;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "speaker")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Speaker {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "speaker", cascade = CascadeType.REMOVE)
    private List<Lecture> lectures;
}

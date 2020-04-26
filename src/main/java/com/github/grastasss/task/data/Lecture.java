package com.github.grastasss.task.data;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lecture")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Lecture {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "speaker_id")
    private Speaker speaker;
}

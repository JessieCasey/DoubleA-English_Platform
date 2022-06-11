package com.doubleA.platform.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "club")
@Getter
@Setter
public class Club {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Student> students;

    @OneToOne
    @JoinColumn(name = "class_monitor_id")
    private Student classMonitor;

    @Enumerated(EnumType.STRING)
    private Level level;
}



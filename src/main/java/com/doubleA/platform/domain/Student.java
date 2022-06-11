package com.doubleA.platform.domain;

import com.doubleA.platform.domain.lesson.Lesson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student")
@Getter
@Setter
public class Student {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(mappedBy = "students")
    private List<Lesson> lessons;

    @OneToOne(mappedBy = "student")
    private Tuition tuition;

    @ManyToOne(optional = false)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "balance")
    private Double balance;

    @OneToOne(mappedBy = "classMonitor")
    private Club headOfClub;

    public Student() {
    }
}


package com.doubleA.platform.domain;

import com.doubleA.platform.domain.lesson.Lesson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teacher")
@Getter
@Setter
public class Teacher {
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

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Lesson> lessonsCreated;

    public Teacher() {
    }
}

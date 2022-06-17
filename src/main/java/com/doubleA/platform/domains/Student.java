package com.doubleA.platform.domains;

import com.doubleA.platform.domains.lesson.Lesson;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
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

    @JsonIgnore
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @ManyToMany(mappedBy = "students")
    private List<Lesson> lessons;

    @OneToOne(mappedBy = "student")
    private Tuition tuition;

    @ManyToOne()
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "balance")
    private Double balance;

    @OneToOne(mappedBy = "classMonitor")
    private Club headOfClub;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "student_roles",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public Student() {
    }
}


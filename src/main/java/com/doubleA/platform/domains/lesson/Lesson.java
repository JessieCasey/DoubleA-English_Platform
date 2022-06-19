package com.doubleA.platform.domains.lesson;

import com.doubleA.platform.domains.Level;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "lesson")
public class Lesson {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date time;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    private Teacher creator;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "lesson_students",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    public Lesson(String title, String description, Date time, Level level, Type type) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.level = level;
        this.type = type;
    }

    public Lesson() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (!Objects.equals(id, lesson.id)) return false;
        return Objects.equals(title, lesson.title);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}

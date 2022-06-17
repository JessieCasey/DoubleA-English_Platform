package com.doubleA.platform.repositories;

import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.domains.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {
    Iterable<Lesson> findAllByCreator(Teacher teacher);
    boolean existsByTitleAndCreator(String title, Teacher teacher);
}

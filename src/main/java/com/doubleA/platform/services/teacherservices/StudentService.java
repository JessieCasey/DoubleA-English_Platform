package com.doubleA.platform.services.teacherservices;

import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.lesson.Lesson;
import com.doubleA.platform.exceptions.LessonNotFoundException;
import com.doubleA.platform.repositories.LessonRepository;
import com.doubleA.platform.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final LessonRepository lessonRepository;

    private final StudentRepository studentRepository;

    public StudentService(LessonRepository lessonRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.studentRepository = studentRepository;
    }

    public void setScores(Student student, UUID lessonId, Integer score) throws LessonNotFoundException {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        Map<Lesson, Integer> scoresMap = student.getScoresMap();
        scoresMap.put(lesson.get(), score);
        studentRepository.save(student);
    }
}

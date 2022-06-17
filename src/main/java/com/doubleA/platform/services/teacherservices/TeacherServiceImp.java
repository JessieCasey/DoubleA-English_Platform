package com.doubleA.platform.services.teacherservices;

import com.doubleA.platform.domains.lesson.Lesson;
import com.doubleA.platform.exceptions.LessonNotFoundException;
import com.doubleA.platform.repositories.LessonRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeacherServiceImp {


    private final LessonRepository lessonRepository;

    private final TeacherRepository teacherRepository;

    public TeacherServiceImp(LessonRepository lessonRepository, TeacherRepository teacherRepository) {
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
    }


}

package com.doubleA.platform.services.teacherservices;

import com.doubleA.platform.domains.Level;
import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.domains.lesson.Lesson;
import com.doubleA.platform.domains.lesson.Type;
import com.doubleA.platform.payloads.LessonDTO;
import com.doubleA.platform.repositories.LessonRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class LessonServiceImp {

    private final LessonRepository lessonRepository;

    private final TeacherRepository teacherRepository;

    public LessonServiceImp(LessonRepository lessonRepository, TeacherRepository teacherRepository) {
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
    }

    public void addLesson(LessonDTO lessonDTO, Teacher teacher) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(lessonDTO.getTime()); // 31/12/1998
        Level level = Level.valueOf(lessonDTO.getLevel()); // string to level
        Type type = Type.valueOf(lessonDTO.getType()); // string to type

        Lesson lesson = new Lesson(
                lessonDTO.getTitle(),
                lessonDTO.getDescription(),
                date,
                level,
                type
        );

        lesson.setId(UUID.randomUUID());
        lesson.setCreator(teacher);

        lessonRepository.save(lesson);
    }
}

package com.doubleA.platform.services.teacherservices;

import com.doubleA.platform.domains.Level;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.domains.lesson.Lesson;
import com.doubleA.platform.domains.lesson.Type;
import com.doubleA.platform.exceptions.LessonNotFoundException;
import com.doubleA.platform.payloads.LessonDTO;
import com.doubleA.platform.repositories.LessonRepository;
import com.doubleA.platform.repositories.StudentRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImp {

    private final LessonRepository lessonRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    public LessonServiceImp(LessonRepository lessonRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.lessonRepository = lessonRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public ResponseEntity addLesson(LessonDTO lessonDTO, Teacher teacher) throws ParseException {
        String title = lessonDTO.getTitle();
        if (lessonRepository.existsByTitleAndCreator(title, teacher)) {
            return new ResponseEntity<>("Lesson with title [" + title + "] is already exists", HttpStatus.BAD_REQUEST);
        }

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

        return new ResponseEntity<>("Lesson with title [" + title + "] is already added", HttpStatus.OK);
    }

    public Iterable<Lesson> getAllLessonsByTeacher(UUID teacherId) throws LessonNotFoundException {
        Iterable<Lesson> lessons = null;
        if (!teacherRepository.findById(teacherId).isPresent()) {
            throw new LessonNotFoundException("The teacher doesn't have any advertisements");
        } else {
            lessons = lessonRepository.findAllByCreator(teacherRepository.findById(teacherId).get());
            if (lessons != null) {
                return lessons;
            } else {
                throw new LessonNotFoundException("The teacher doesn't have any advertisements");
            }
        }
    }

    public Iterable<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public void deleteLessonById(UUID lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    public ResponseEntity<String> enrollStudentToLesson(Student student, UUID lessonId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isPresent()) {
            student.getLessons().add(lesson.get());
            lesson.get().getStudents().add(student);

            lessonRepository.save(lesson.get());
            studentRepository.save(student);

            return new ResponseEntity<>("Student " + student.getFirstname() + " Enrolled to " + lesson.get().getTitle(), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    public ResponseEntity<String> leaveLesson(Student student, UUID lessonId) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isPresent() && student.getLessons().contains(lesson.get())) {

            lesson.get().getStudents().remove(student);
            student.getLessons().remove(lesson.get());

            lessonRepository.save(lesson.get());
            studentRepository.save(student);

            return new ResponseEntity<>("Student " + student.getFirstname() + " left the " + lesson.get().getTitle(), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}

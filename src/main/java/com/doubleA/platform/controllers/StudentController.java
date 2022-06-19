package com.doubleA.platform.controllers;

import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.lesson.Lesson;
import com.doubleA.platform.security.services.CustomUserDetailsService;
import com.doubleA.platform.services.teacherservices.LessonServiceImp;
import com.doubleA.platform.services.teacherservices.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    private final LessonServiceImp lessonService;

    private final CustomUserDetailsService customUserDetailsService;

    public StudentController(LessonServiceImp lessonService, CustomUserDetailsService customUserDetailsService, StudentService studentService) {
        this.lessonService = lessonService;
        this.customUserDetailsService = customUserDetailsService;
        this.studentService = studentService;
    }

    @PostMapping("enroll/{lessonId}")
    public ResponseEntity enrollLesson(Principal user, @PathVariable UUID lessonId) {
        try {
            Student student = (Student) customUserDetailsService.loadStudentOrTeacher(user.getName()).student();
            if (student != null)
                return ResponseEntity.ok(lessonService.enrollStudentToLesson(student, lessonId));
            else
                throw new Exception();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available \n" + e);
        }
    }

    @PostMapping("leave/{lessonId}")
    public ResponseEntity leaveLesson(Principal user, @PathVariable UUID lessonId) {
        try {
            Student student = (Student) customUserDetailsService.loadStudentOrTeacher(user.getName()).student();
            return ResponseEntity.ok(lessonService.leaveLesson(student, lessonId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available");
        }
    }

    @GetMapping("grades")
    public ResponseEntity getGrades(Principal user) {
        try {
            Student student = (Student) customUserDetailsService.loadStudentOrTeacher(user.getName()).student();
            Map<String, Integer> scoresToPrint = new HashMap<>();

            for (Map.Entry<Lesson, Integer> entry : student.getScoresMap().entrySet()) {
                scoresToPrint.put(entry.getKey().getTitle(), entry.getValue());
            }

            return ResponseEntity.ok(scoresToPrint);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available \n" + e);
        }
    }

    @PostMapping("grades/{lessonId}")
    public ResponseEntity setGrade(Principal user, @PathVariable UUID lessonId, @RequestBody Integer score) {
        try {
            Student student = (Student) customUserDetailsService.loadStudentOrTeacher(user.getName()).student();
            studentService.setScores(student, lessonId, score);
            return ResponseEntity.ok("Done");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available \n" + e);
        }
    }
}

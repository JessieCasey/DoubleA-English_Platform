package com.doubleA.platform.controllers;

import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.domains.lesson.Lesson;
import com.doubleA.platform.payloads.LessonDTO;
import com.doubleA.platform.security.services.CustomUserDetailsService;
import com.doubleA.platform.services.teacherservices.LessonServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private final LessonServiceImp lessonService;

    private final CustomUserDetailsService customUserDetailsService;

    public LessonController(LessonServiceImp lessonService, CustomUserDetailsService customUserDetailsService) {
        this.lessonService = lessonService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/add")
    public ResponseEntity addLesson(@RequestBody LessonDTO lessonDTO, Principal user) {
        try {
            Teacher teacher = (Teacher) customUserDetailsService.loadStudentOrTeacher(user.getName()).teacher();
            return lessonService.addLesson(lessonDTO, teacher);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson isn't available: \n" + e);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<String> deleteLesson(@PathVariable UUID lessonId) {
        try {
            lessonService.deleteLessonById(lessonId);
            return ResponseEntity.ok("Lesson was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson isn't available: \n" + e);
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity getAllLessonsByTeacher(@PathVariable UUID teacherId) {
        try {
            return ResponseEntity.ok(lessonService.getAllLessonsByTeacher(teacherId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available");
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity getAllLessonsByStudent(@PathVariable UUID studentId) {
        try {
            return ResponseEntity.ok(lessonService.getAllLessonsByTeacher(studentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available");
        }
    }

    @GetMapping
    public ResponseEntity getAllLessons() {
        try {
            Iterable<Lesson> allLessons = lessonService.getAllLessons();
            return ResponseEntity.ok(allLessons);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available");
        }
    }


}

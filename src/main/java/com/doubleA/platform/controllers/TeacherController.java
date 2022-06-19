package com.doubleA.platform.controllers;

import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.security.services.CustomUserDetailsService;
import com.doubleA.platform.services.teacherservices.LessonServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final LessonServiceImp lessonService;

    private final CustomUserDetailsService customUserDetailsService;

    public TeacherController(LessonServiceImp lessonService, CustomUserDetailsService customUserDetailsService) {
        this.lessonService = lessonService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping
    public ResponseEntity getAllLessonsByTeacher(Principal user) {
        try {
            Teacher teacher = (Teacher) customUserDetailsService.loadStudentOrTeacher(user.getName()).teacher();
            return ResponseEntity.ok(lessonService.getAllLessons());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson aren't available");
        }
    }
}

package com.doubleA.platform.controllers;

import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.payloads.LessonDTO;
import com.doubleA.platform.security.services.CustomUserDetailsService;
import com.doubleA.platform.services.teacherservices.LessonServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth/lesson")
public class LessonController {

    private final LessonServiceImp lessonService;

    private final CustomUserDetailsService customUserDetailsService;

    public LessonController(LessonServiceImp lessonService, CustomUserDetailsService customUserDetailsService) {
        this.lessonService = lessonService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping
    public ResponseEntity<String> addLesson(@RequestBody LessonDTO lessonDTO, Principal student) {
        try {
            Teacher teacher = (Teacher) customUserDetailsService.loadStudentOrTeacher(student.getName()).teacher();
            lessonService.addLesson(lessonDTO, teacher);
            return new ResponseEntity<>("Lesson " + lessonDTO.getTitle() + " added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lesson isn't available: \n" + e);
        }

    }
}

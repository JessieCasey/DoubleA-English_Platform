package com.doubleA.platform.controllers;

import com.doubleA.platform.payloads.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonController {
    public ResponseEntity<String> addLesson(@RequestBody LoginDTO loginDTO) {

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}

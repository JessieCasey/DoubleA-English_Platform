package com.doubleA.platform.controllers;


import com.doubleA.platform.domains.Role;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.payloads.LoginDTO;
import com.doubleA.platform.payloads.SignUpDTO;
import com.doubleA.platform.repositories.RoleRepository;
import com.doubleA.platform.repositories.StudentRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){

        // add check for email exists in a DB
        if(studentRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create student object
        Student student = new Student();
        UUID studentID = UUID.randomUUID();

        student.setId(studentID);
        student.setFirstname(student.getFirstname());
        student.setLastname(student.getLastname());

        student.setEmail(signUpDto.getEmail());
        student.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        //Role roles = roleRepository.findByName(role).get();
        //student.setRoles(Collections.singleton(roles));

        studentRepository.save(student);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/studentsList")
    public ResponseEntity<?> getAllStudents(){
        try {
            return ResponseEntity.ok(studentRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Students aren't available");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/teachersList")
    public ResponseEntity<?> getAllTeachers(){
        try {
            return ResponseEntity.ok(teacherRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Teachers aren't available");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<?> deleteTeacherById(@PathVariable UUID id){
        try {
            teacherRepository.deleteById(id);
            return ResponseEntity.ok("Teacher was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Teachers is unavailable");
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable UUID id){
        try {
            studentRepository.deleteById(id);
            return ResponseEntity.ok("Student was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Student is unavailable");
        }
    }

}

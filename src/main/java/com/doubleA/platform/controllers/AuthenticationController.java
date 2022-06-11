package com.doubleA.platform.controllers;


import com.doubleA.platform.domains.Student;
import com.doubleA.platform.payloads.LoginDTO;
import com.doubleA.platform.payloads.SignUpDTO;
import com.doubleA.platform.repositories.RoleRepository;
import com.doubleA.platform.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

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

        //Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        //student.setRoles(Collections.singleton(roles));

        studentRepository.save(student);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}

package com.doubleA.platform.bootstrap;

import com.doubleA.platform.domains.Role;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.repositories.RoleRepository;
import com.doubleA.platform.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final StudentRepository studentRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        studentRepository.deleteAll();
        roleRepository.deleteAll();

        // create role
        Role role = new Role();
        role.setName("Student");
        roleRepository.save(role);

        // create student object
        Student student = new Student();
        UUID studentID = UUID.randomUUID();

        student.setId(studentID);
        student.setFirstname("Jessie");
        student.setLastname("Casey");

        student.setEmail("casey@gmail.com");
        student.setPassword(passwordEncoder.encode("casey"));

        Role roles = roleRepository.findByName("Student").get();
        student.setRoles(Collections.singleton(roles));

        studentRepository.save(student);

    }
}

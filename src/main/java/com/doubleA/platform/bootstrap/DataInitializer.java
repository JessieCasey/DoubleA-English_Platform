package com.doubleA.platform.bootstrap;

import com.doubleA.platform.domains.Role;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.repositories.RoleRepository;
import com.doubleA.platform.repositories.StudentRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        roleRepository.deleteAll();

        studentInit();
        teacherInit();
    }

    public void studentInit() {
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

    public void teacherInit() {
        // create role
        Role role = new Role();
        role.setName("Teacher");
        roleRepository.save(role);

        // create teacher object
        Teacher teacher = new Teacher();
        UUID teacherID = UUID.randomUUID();

        teacher.setId(teacherID);
        teacher.setFirstname("Tony");
        teacher.setLastname("Ket");

        teacher.setEmail("tony@gmail.com");
        teacher.setPassword(passwordEncoder.encode("tony"));

        Role roles = roleRepository.findByName("Teacher").get();
        teacher.setRoles(Collections.singleton(roles));

        teacherRepository.save(teacher);
    }
}

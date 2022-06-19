package com.doubleA.platform.bootstrap;

import com.doubleA.platform.domains.Role;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.payloads.LessonDTO;
import com.doubleA.platform.repositories.LessonRepository;
import com.doubleA.platform.repositories.RoleRepository;
import com.doubleA.platform.repositories.StudentRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import com.doubleA.platform.services.teacherservices.LessonServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collections;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    private final LessonRepository lessonRepository;

    private final PasswordEncoder passwordEncoder;

    private final LessonServiceImp lessonService;

    public DataInitializer(RoleRepository roleRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, PasswordEncoder passwordEncoder, LessonRepository lessonRepository, LessonServiceImp lessonService) {
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.lessonRepository = lessonRepository;
        this.lessonService = lessonService;
    }

    @Override
    public void run(String... args) throws ParseException {
        deleteAllRepositories();

        adminInit();
        Student student = studentInit();
        Teacher teacher = teacherInit();

        lessonInit(teacher);
    }

    public void deleteAllRepositories() {
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        roleRepository.deleteAll();
        lessonRepository.deleteAll();
        roleRepository.deleteAll();
    }

    public void lessonInit(Teacher teacher) throws ParseException {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setTitle("Travel lesson");
        lessonDTO.setDescription("Lesson about travel, similar vocabulary and etc.");
        lessonDTO.setTime("31/12/1998");
        lessonDTO.setLevel("A1");
        lessonDTO.setType("VOCABULARY");

        lessonService.addLesson(lessonDTO, teacher);

    }

    public Student studentInit() {
        // create role
        Role role = new Role();
        role.setName("STUDENT");
        roleRepository.save(role);

        // create student object
        Student student = new Student();
        UUID studentID = UUID.randomUUID();

        student.setId(studentID);
        student.setFirstname("Jessie");
        student.setLastname("Casey");

        student.setEmail("casey@gmail.com");
        student.setPassword(passwordEncoder.encode("casey"));

        Role roles = roleRepository.findByName("STUDENT").get();
        student.setRoles(Collections.singleton(roles));

        studentRepository.save(student);

        return student;
    }

    public Teacher teacherInit() {
        // create role
        Role role = new Role();
        role.setName("TEACHER");
        roleRepository.save(role);

        // create teacher object
        Teacher teacher = new Teacher();
        UUID teacherID = UUID.randomUUID();

        teacher.setId(teacherID);
        teacher.setFirstname("Tony");
        teacher.setLastname("Ket");

        teacher.setEmail("tony@gmail.com");
        teacher.setPassword(passwordEncoder.encode("tony"));

        Role roles = roleRepository.findByName("TEACHER").get();
        teacher.setRoles(Collections.singleton(roles));

        teacherRepository.save(teacher);
        return teacher;
    }

    public void adminInit() {
        Role role = new Role();
        role.setName("ADMIN");
        roleRepository.save(role);

        Teacher teacher = new Teacher();
        UUID teacherID = UUID.randomUUID();

        teacher.setId(teacherID);
        teacher.setFirstname("admin");
        teacher.setLastname("admin");

        teacher.setEmail("admin@doubleA.com");
        teacher.setPassword(passwordEncoder.encode("admin"));

        Role roles = roleRepository.findByName("ADMIN").get();
        teacher.setRoles(Collections.singleton(roles));

        teacherRepository.save(teacher);
    }
}

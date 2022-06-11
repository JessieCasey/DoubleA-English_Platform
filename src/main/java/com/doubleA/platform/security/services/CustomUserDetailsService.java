package com.doubleA.platform.security.services;

import com.doubleA.platform.domains.Role;
import com.doubleA.platform.domains.Student;
import com.doubleA.platform.domains.Teacher;
import com.doubleA.platform.repositories.StudentRepository;
import com.doubleA.platform.repositories.TeacherRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    public CustomUserDetailsService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Student student = studentRepository.findByEmail(email).get();
            return new User(student.getEmail(), student.getPassword(), mapRolesToAuthorities(student.getRoles()));
        } catch (UsernameNotFoundException e) {
            Teacher teacher = teacherRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email:" + email));
            return new User(teacher.getEmail(), teacher.getPassword(), mapRolesToAuthorities(teacher.getRoles()));
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

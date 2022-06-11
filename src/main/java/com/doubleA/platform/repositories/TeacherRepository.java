package com.doubleA.platform.repositories;

import com.doubleA.platform.domains.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    Optional<Teacher> findByEmail(String email);

    Boolean existsByEmail(String email);
}

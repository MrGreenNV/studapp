package ru.miit.webapp.repositories;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.miit.webapp.models.Group;
import ru.miit.webapp.models.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByStudentName(String studentName);

    Optional<Student> findByUsername(String username);

    List<Student> findAllByGroup(Group group);

    @Modifying
    @Transactional
    void deleteByStudentName(String studentName);
}

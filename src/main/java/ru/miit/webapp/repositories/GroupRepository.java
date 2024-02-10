package ru.miit.webapp.repositories;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.miit.webapp.models.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    Optional<Group> findByGroupName(String groupName);

    @Modifying
    @Transactional
    void deleteByGroupName(String groupName);
}

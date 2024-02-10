package ru.miit.webapp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.miit.webapp.models.Group;
import ru.miit.webapp.models.Role;
import ru.miit.webapp.models.Student;
import ru.miit.webapp.models.User;
import ru.miit.webapp.models.UserRoles;
import ru.miit.webapp.repositories.GroupRepository;
import ru.miit.webapp.repositories.RoleRepository;
import ru.miit.webapp.repositories.StudentRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Init implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    private final String defaultPass;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initGroups();
        initUsers();
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            var unknownRole =   new Role(UserRoles.UNKNOWN);
            var adminRole =     new Role(UserRoles.ADMIN);
            var moderatorRole = new Role(UserRoles.MODERATOR);
            var studentRole =    new Role(UserRoles.STUDENT);

            roleRepository.save(unknownRole);
            roleRepository.save(adminRole);
            roleRepository.save(moderatorRole);
            roleRepository.save(studentRole);
        }
    }

    private void initGroups() {
        if (groupRepository.count() == 0) {
            groupRepository.save(new Group("УВПв-421", null));
            groupRepository.save(new Group("УВПв-321", null));
            groupRepository.save(new Group("УВПв-221", null));
        }
    }

    private void initUsers() {
        if (studentRepository.count() == 0) {
            initAdmin();
            initModerator();
            initStudent("Vano","Иванов","УВПв-221");
            initStudent("Petro","Петров","УВПв-221");
            initStudent("Sidro","Сидоров","УВПв-321");
            initStudent("Vedro","Ведров","УВПв-321");
            initStudent("Leto","Летов","УВПв-221");
        }
    }

    private void initAdmin() {
        var adminRole = roleRepository.findRoleByName(UserRoles.ADMIN).orElseThrow();

        var adminUser = new Student(
                "Admin",
                passwordEncoder.encode(defaultPass)
        );

        adminUser.setRoles(List.of(adminRole));
        adminUser.setStudentName("Admin");
        studentRepository.save(adminUser);
    }

    private void initModerator() {
        var moderatorRole = roleRepository.findRoleByName(UserRoles.MODERATOR).orElseThrow();

        var moderatorUser = new Student(
                "Moderator",
                passwordEncoder.encode(defaultPass)
        );

        moderatorUser.setRoles(List.of(moderatorRole));
        moderatorUser.setStudentName("Moderator");
        studentRepository.save(moderatorUser);
    }

    private void initStudent(String username, String name, String groupName) {
        var studentRole = roleRepository.findRoleByName(UserRoles.STUDENT).orElseThrow();

        Group group = groupRepository.findByGroupName(groupName).orElseThrow();

        var studentUser = new Student(
                username,
                passwordEncoder.encode(defaultPass),
                name,
                group
        );

        group.getStudents().add(studentUser);
        groupRepository.save(group);

        studentUser.setRoles(List.of(studentRole));
        studentRepository.save(studentUser);
    }

}

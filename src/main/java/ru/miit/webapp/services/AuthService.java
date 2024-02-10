package ru.miit.webapp.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.miit.webapp.dto.StudentRegistrationDto;
import ru.miit.webapp.dto.UserRegistrationDto;
import ru.miit.webapp.models.Group;
import ru.miit.webapp.models.Role;
import ru.miit.webapp.models.Student;
import ru.miit.webapp.models.User;
import ru.miit.webapp.models.UserRoles;
import ru.miit.webapp.repositories.GroupRepository;
import ru.miit.webapp.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentService studentService;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Transactional
    public void register(StudentRegistrationDto registrationDto) {
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
            throw new RuntimeException("Пароли не совпадают");
        }

        User user = modelMapper.map(registrationDto, Student.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        switch (registrationDto.getUserType()) {
            case STUDENT -> {
                Role studentRole = roleRepository.findRoleByName(UserRoles.STUDENT).orElseThrow(() -> new RuntimeException("Роль не найдена"));

                Student student = (Student) user;
                student.setRoles(List.of(studentRole));
                student.setStudentName(registrationDto.getStudentName());

                if (registrationDto.getGroupName() == null) {
                    throw new RuntimeException("Группа студента не указана");
                }

                Group group = groupRepository.findByGroupName(registrationDto.getGroupName()).orElseThrow(() -> new RuntimeException("Группа не найдена"));
                student.setGroup(group);
                group.getStudents().add(student);

                studentService.saveStudent(student);
                groupRepository.save(group);
            }
            default -> throw new RuntimeException("Тип пользователя не определен!");
        }
    }
}

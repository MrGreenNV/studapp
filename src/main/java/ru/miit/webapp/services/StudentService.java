package ru.miit.webapp.services;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.miit.webapp.dto.ShowDetailsStudentDto;
import ru.miit.webapp.dto.ShowStudentInfoDto;
import ru.miit.webapp.models.Group;
import ru.miit.webapp.models.Student;
import ru.miit.webapp.models.User;
import ru.miit.webapp.repositories.GroupRepository;
import ru.miit.webapp.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository, GroupRepository groupRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudent(String studentName) {
        return studentRepository.findByStudentName(studentName).orElseThrow(() -> new UsernameNotFoundException("Студент с именем " + studentName + " не найден"));
    }

    public Student getUser(String username) {
        return studentRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));
    }

    public List<ShowDetailsStudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student -> modelMapper.map(student, ShowDetailsStudentDto.class))
                .collect(Collectors.toList());
    }
    public ShowDetailsStudentDto getStudentInfo(String studentName) {
        return modelMapper.map(studentRepository.findByStudentName(studentName), ShowDetailsStudentDto.class);
    }

    @Transactional
    public void expelStudent(String studentName) {
        Student student = studentRepository.findByStudentName(studentName).orElse(null);
        if (student != null) {
            Group group = student.getGroup();
            if (group != null) {
                student.setGroup(null);
                Group saveGroup = groupRepository.findByGroupName(group.getGroupName()).orElse(null);
                if (saveGroup != null) {
                    saveGroup.getStudents().remove(student);
                    groupRepository.save(saveGroup);
                }
            }

        }
        studentRepository.deleteByStudentName(studentName);
    }

    public ShowDetailsStudentDto studentInfo(String studentName) {
        return modelMapper.map(studentRepository.findByStudentName(studentName), ShowDetailsStudentDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUser(username);
    }
}

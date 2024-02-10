package ru.miit.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.miit.webapp.services.GroupService;
import ru.miit.webapp.services.StudentService;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private final StudentService studentService;
    private final GroupService groupService;

    @Autowired
    public StudentsController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public String showAllStudents(Model model) {
        model.addAttribute("allStudents", studentService.getAllStudents());
        return "students-all";
    }

    @GetMapping("/student-details/{student-name}")
    public String showStudentDetails(@PathVariable("student-name") String studentName, Model model) {
        model.addAttribute("studentDetails", studentService.studentInfo(studentName));
        return "student-details";
    }

    @GetMapping("/student-delete/{student-name}")
    public String deleteStudent(@PathVariable("student-name") String studentName) {
        studentService.expelStudent(studentName);

        return "redirect:/students/all";
    }
}

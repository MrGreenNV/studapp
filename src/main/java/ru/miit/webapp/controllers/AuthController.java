package ru.miit.webapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.miit.webapp.dto.ShowDetailsStudentDto;
import ru.miit.webapp.dto.StudentRegistrationDto;
import ru.miit.webapp.dto.UserRegistrationDto;
import ru.miit.webapp.models.Student;
import ru.miit.webapp.models.UserRoles;
import ru.miit.webapp.repositories.GroupRepository;
import ru.miit.webapp.services.AuthService;
import ru.miit.webapp.services.GroupService;
import ru.miit.webapp.services.StudentService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private final StudentService studentService;
    private final GroupService groupService;

    @ModelAttribute("studentRegistrationDto")
    public StudentRegistrationDto initForm() {
        return new StudentRegistrationDto();
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("allGroups", groupService.allGroups());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid StudentRegistrationDto userRegistrationDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDto", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);

            return "redirect:/users/register";
        }

        authService.register(userRegistrationDTO);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();

        Student student = studentService.getUser(username);

        ShowDetailsStudentDto showDetailsStudentDto = new ShowDetailsStudentDto();
        showDetailsStudentDto.setUsername(username);
        showDetailsStudentDto.setStudentName(student.getStudentName());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.STUDENT.name()))) {
            showDetailsStudentDto.setGroupName(student.getGroup().getGroupName());
        }

        model.addAttribute("user", showDetailsStudentDto);

        return "profile";
    }
}

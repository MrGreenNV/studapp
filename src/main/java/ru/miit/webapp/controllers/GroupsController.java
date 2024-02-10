package ru.miit.webapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.miit.webapp.dto.AddGroupDto;
import ru.miit.webapp.repositories.GroupRepository;
import ru.miit.webapp.repositories.StudentRepository;
import ru.miit.webapp.services.GroupService;
import ru.miit.webapp.services.StudentService;

@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupsController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;


    @GetMapping("/add")
    public String addGroup() {
        return "group-add";
    }

    @ModelAttribute("groupModel")
    public AddGroupDto initGroup() {
        return new AddGroupDto();
    }

    @PostMapping("/add")
    public String addGroup(@Valid AddGroupDto groupModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("groupModel", groupModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.groupModel",
                    bindingResult);
            return "redirect:/groups/add";
        }
        groupService.addGroup(groupModel);

        return "redirect:/";
    }

    @GetMapping("/all")
    public String showAllGroups(Model model) {
        model.addAttribute("groupInfos", groupService.allGroups());

        return "groups-all";
    }

    @GetMapping("/group-details/{group-name}")
    public String groupDetails(@PathVariable("group-name") String groupName, Model model) {
        model.addAttribute("groupDetails", groupService.groupInfo(groupName));
        model.addAttribute("allStudents", studentRepository.findAllByGroup(groupRepository.findByGroupName(groupName).orElseThrow()));
        return "group-details";
    }

    @GetMapping("/group-delete/{group-name}")
    public String deleteGroup(@PathVariable("group-name") String groupName) {
        groupService.removeGroup(groupName);

        return "redirect:/groups/all";
    }
}

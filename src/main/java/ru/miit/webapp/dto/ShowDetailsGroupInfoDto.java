package ru.miit.webapp.dto;

import ru.miit.webapp.models.Student;

import java.util.Set;

public class ShowDetailsGroupInfoDto {
    private String groupName;

    private Set<Student> students;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}

package ru.miit.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс описывает группу, за которой закреплены студенты.
 */
@Entity
@Table(name = "groups")
public class Group extends BaseEntity {

    /** Название группы */
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String groupName;

    /** Студенты в группе */
    @OneToMany(mappedBy = "group", targetEntity = Student.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Student> students;

    public Group() {
        students = new HashSet<>();
    }

    public Group(String groupName, Set<Student> students) {
        this.groupName = groupName;
        this.students = students;
    }

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
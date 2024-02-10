package ru.miit.webapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс описывает студента.
 */
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends User {

    /**
     * Имя студента
     */
    @Column(columnDefinition = "VARCHAR(50)")
    private String studentName;

    /**
     * Группа студента
     */
    @ManyToOne
    @JsonIgnore
    private Group group;

    public Student() {
    }

    public Student(String studentName, Group group) {
        this.studentName = studentName;
        this.group = group;
    }

    public Student(String username, String password, String studentName, Group group) {
        super(username, password);
        this.studentName = studentName;
        this.group = group;
    }

    public Student(String username, String password) {
        super(username, password);
    }
}

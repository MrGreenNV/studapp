package ru.miit.webapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AddGroupDto {

    @NotEmpty(message = "Название группы не должно быть пустым.")
    @Size(min = 5, max = 20, message = "Название группы должно содержать от 5 до 20 символов.")
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

package ru.miit.webapp.models;

import lombok.Getter;

@Getter
public enum UserType {
    STUDENT(0);

    private final int id;

    UserType(int code) {
        this.id = code;
    }

    public static UserType fromId(int code) {
        return UserType.values()[code];
    }

}

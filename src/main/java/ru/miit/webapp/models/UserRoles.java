package ru.miit.webapp.models;

import lombok.Getter;

/**
 * Набор ролей пользователей
 */
@Getter
public enum UserRoles {
    UNKNOWN(0),
    ADMIN(1),
    MODERATOR(2),
    STUDENT(3);

    private final int id;

    UserRoles(int code) {
        this.id = code;
    }

    public static UserRoles fromId(int code) {

        UserRoles[] roles = UserRoles.values();
        if (code <= 0 || code > roles.length) {
            return UserRoles.UNKNOWN;
        }
        return roles[code];
    }
}

package ru.miit.webapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.miit.webapp.models.UserType;

/**
 * DTO для регистрации нового пользователя
 */
@Getter
@Setter
public class UserRegistrationDto {
    @NotEmpty(message = "Поле не должно быть пустым.")
    @Size(min = 3, max = 255, message = "В имени должно быть от 3 до 255 символов.")
    private String username;

    @NotEmpty(message = "Поле не должно быть пустым.")
    @Size(min = 8, max = 255, message = "Пароль должен содержать от 8 до 255 символов.")
    private String password;

    @NotEmpty(message = "Поле не должно быть пустым.")
    private String confirmPassword;

    private UserType userType = UserType.STUDENT;

}

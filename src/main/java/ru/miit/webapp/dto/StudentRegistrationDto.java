package ru.miit.webapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mrGreenNV
 */
@Getter
@Setter
@NoArgsConstructor
public class StudentRegistrationDto extends UserRegistrationDto {
    @NotEmpty(message = "Имя студента не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя студента должно содержать от 2 до 50 символов.")
    private String studentName;

    private String groupName;
}

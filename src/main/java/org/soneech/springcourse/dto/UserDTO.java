package org.soneech.springcourse.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String username;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;

    @Email
    @NotEmpty(message = "Почта не может быть пустой")
    private String email;
}

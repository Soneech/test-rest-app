package org.soneech.springcourse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Укажите имя")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String username;

    @Min(value = 0, message = "Возраст не может быть отрицательным")
    private int age;

    @Email
    @NotEmpty(message = "Укажите почту")
    private String email;

    public User(String username, int age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
    }
}

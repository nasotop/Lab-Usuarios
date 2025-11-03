package com.lab.usuarios.lab_usuarios.presentation.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private Long id;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}

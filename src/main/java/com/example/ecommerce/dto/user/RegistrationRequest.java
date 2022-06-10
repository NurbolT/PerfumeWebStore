package com.example.ecommerce.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotBlank(message = "First name cannot be empty.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    @Email(message = "Incorrect email.")
    @NotBlank(message = "Email cannot be empty.")
    private String email;

    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long.")
    private String password;

    @Size(min = 6, max = 16, message = "The password confirmation must be between 6 and 16 characters long.")
    private String password2;

}

package com.spring.internal_working.internal_work.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticateUserDto {



    @Email
    @NotBlank(message = "email is needed")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}

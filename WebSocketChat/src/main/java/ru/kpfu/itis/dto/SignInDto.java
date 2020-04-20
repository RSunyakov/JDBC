package ru.kpfu.itis.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SignInDto {
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}

package ru.springuser.dto;

import lombok.*;
import ru.springuser.annotation.DateConstraint;
import ru.springuser.annotation.EmailConstraint;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class SignUpDto {
    @NotNull
    @EmailConstraint
    private String email;

    @NotNull
    @Size(min = 5)
    private String password;

    @NotNull
    @NotEmpty
    private String country;

    @NotNull
    @NotEmpty
    private String gender;

    @DateConstraint
    private String birthday;
}

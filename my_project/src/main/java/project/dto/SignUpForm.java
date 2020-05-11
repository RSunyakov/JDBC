package project.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpForm {
    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @Email
    @NotEmpty
    private String email;

    @Size(min = 6)
    private String password;
}

package project.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Locale;
import java.util.ResourceBundle;

@Data
public class ProfileForm {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Min(value = 0)
    private Integer age;


}

package ru.kpfu.itis.dto;

import lombok.Data;
import ru.kpfu.itis.models.Auditorium;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SignUpDto {
    @Email(message = "{errors.incorrect.email}")
    @NotNull(message = "{errors.null.email}")
    private String email;
    @Size(min = 5, message = "{errors.incorrect.password}")
    @NotNull(message = "{errors.null.password}")
    private String password;
    @NotNull(message = "{errors.null.firstName}")
    private String firstName;
    @NotNull(message = "{errors.null.lastName}")
    private String lastName;
    @NotNull(message = "{errors.null.gender}")
    private String gender;
    private List<Auditorium> auditoriumList;
    @NotNull(message = "{errors.null.typeOfStudent}")
    private String typeOfStudent;

}

package ru.kpfu.itis.dto;

import lombok.Data;
import ru.kpfu.itis.models.Auditorium;

import java.util.List;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private List<Auditorium> auditoriumList;
    private String typeOfStudent;

}

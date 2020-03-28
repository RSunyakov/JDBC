package ru.springuser.model;

import ru.springuser.annotation.DateConstraint;
import ru.springuser.annotation.EmailConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    private Long id;

    @NotNull
    @EmailConstraint
    private String email;

    private String activationCode;

    @NotNull
    @Size(min = 5)
    private String hashPassword;

    @NotNull
    @NotEmpty
    private String country;

    @NotNull
    @NotEmpty
    private String gender;

    @DateConstraint
    private String birthday;

    private State state;
    private Role role;
}

package ru.kpfu.itis.hateoasservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecturer {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "lecturer")
    private List<Course> courses;

    @ManyToMany
    @JoinTable(name = "lecturers_students",
            joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<Student> students;

}

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
public class Auditorium {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "auditoriums_lessons",
            joinColumns = @JoinColumn(name = "auditorium_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"))
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "auditorium")
    private List<Exam> exams;
}

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
public class Lesson {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToMany(mappedBy = "lessons")
    private List<Auditorium> auditoriums;

}
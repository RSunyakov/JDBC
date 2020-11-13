package ru.kpfu.itis.hateoasservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "course")
    private List<Lesson> lessons;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @ManyToOne
    //TODO: если что-то пойдет не так, то это вот из-за этого
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

   @OneToOne
   private Exam exam;

    private String state;

    public void publish() {

        if (this.state.equals("Draft")) {
            this.state = "Published";
        } else if (this.state.equals("Deleted")) {
            throw new IllegalStateException();
        }
    }

}
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
public class Exam {
    @Id
    @GeneratedValue
    private Long id;

    String name;
    String state;

    @OneToOne
    private Course course;

    @ManyToMany
    @JoinTable(name = "exams_students",
            joinColumns = @JoinColumn(name = "exam_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;


    public void pass() {

        if ((this.state.equals("Scheduled")) || (this.state.equals("Failure"))) {
            this.state = "Passed";
        } else if (this.state.equals("Deleted")) {
            throw new IllegalStateException();
        }
    }

}

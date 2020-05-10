package ru.kpfu.itis.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Marker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private Integer condition;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "auditorium_id")
    @ToString.Exclude
    private Auditorium auditorium;
}

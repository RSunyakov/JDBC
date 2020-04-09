package ru.kpfu.itis.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
public class Auditorium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "auditorium", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private List<Marker> markerList;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "auditoriumList")
    @JsonBackReference
    private List<User> users;
}

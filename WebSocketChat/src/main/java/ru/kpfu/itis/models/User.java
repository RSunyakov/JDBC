package ru.kpfu.itis.models;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String hashPassword;
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messages;

}

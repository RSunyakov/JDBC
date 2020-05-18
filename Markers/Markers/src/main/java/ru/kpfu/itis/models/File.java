package ru.kpfu.itis.models;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String db_Name;
    private Long size;
    private String type;
    private String url;
    private String emailUploader;
    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    private User user;
}

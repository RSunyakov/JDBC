package ru.kpfu.itis.javalabmessagequeue.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Task {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String queueName;
   private String state;

   @JsonBackReference("task-messages")
   @ToString.Exclude
   @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
   @LazyCollection(LazyCollectionOption.FALSE)
   private List<Message> message;
}

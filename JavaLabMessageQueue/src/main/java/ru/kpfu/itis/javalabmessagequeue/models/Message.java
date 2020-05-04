package ru.kpfu.itis.javalabmessagequeue.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Proxy(lazy = false)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Getter
    @Setter
    private String messageId;
    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    private MessageBody messageBody;


    public void accepted() {
        state = "ACCEPTED";
    }

    public void completed() {
        state = "COMPLETED";
    }
}

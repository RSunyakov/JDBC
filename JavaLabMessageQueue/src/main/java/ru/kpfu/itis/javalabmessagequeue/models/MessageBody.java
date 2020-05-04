package ru.kpfu.itis.javalabmessagequeue.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.messaging.handler.annotation.SendTo;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Proxy(lazy = false)
public class MessageBody {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /*private String command;
    private String queueName;
    private String messageId;
    private Object body;*/
    @Getter
    @Setter
    private String body;

    @Getter
    @Setter
    @JsonBackReference("message-body")
    @OneToOne(mappedBy = "messageBody", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Message message;
}

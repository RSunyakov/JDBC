package ru.kpfu.itis.javalabmessagequeue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationExtensionsKt;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import ru.kpfu.itis.javalabmessagequeue.connector.JlmqConnector;
import ru.kpfu.itis.javalabmessagequeue.consumer.JlmqConsumer;
import ru.kpfu.itis.javalabmessagequeue.producer.JlmqProducer;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@Configuration
public class JavaLabMessageQueueApplication {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
       ApplicationContext context = SpringApplication.run(JavaLabMessageQueueApplication.class, args);
       JlmqConnector connector =  (JlmqConnector) context.getBean("jlmqConnector");
       connector.port("8080").connect();
       JlmqProducer producer = connector.producer().toQueue("generate").create();
       producer.send("Hello!");
        JlmqConsumer consumer = connector.consumer().subscribe("generate").onReceive(message -> {
            message.accepted();
            System.out.println(message.getMessageBody().getBody());
            message.completed();
        }).create();
    }


    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }


}

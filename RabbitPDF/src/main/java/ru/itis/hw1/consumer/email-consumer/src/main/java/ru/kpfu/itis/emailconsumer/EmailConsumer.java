package ru.kpfu.itis.emailconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kpfu.itis.emailconsumer.service.EmailServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class EmailConsumer implements CommandLineRunner {
    private static final String MONEY_EMAIL_ROUTING_KEY = "money.email";
    private static final String EXCHANGE_MONEY_NAME = "docs_topic_ex";

    @Autowired
    EmailServiceImpl emailService;

    public static void main(String[] args) {
        SpringApplication.run(EmailConsumer.class, args);
    }

    @Override
    public void run(String... args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_MONEY_NAME, MONEY_EMAIL_ROUTING_KEY);

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                String email = message.getBody().toString().substring(0, message.getBody().toString().indexOf(":") - 1);
                String pdfPath = message.getBody().toString().substring(message.getBody().toString().indexOf(":") + 1);
                emailService.sendMessageWithAttachment(email, "Успех", "Успех", pdfPath);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };

            channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

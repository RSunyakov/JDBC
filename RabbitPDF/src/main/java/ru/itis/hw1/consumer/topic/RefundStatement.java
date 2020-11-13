package ru.itis.hw1.consumer.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ru.itis.hw1.models.User;
import ru.itis.hw1.utils.PdfGenerator;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeoutException;

public class RefundStatement {
    private static final String MONEY_REFUND_ROUTING_KEY = "money.refund";
    private static final String MONEY_EMAIL_ROUTING_KEY = "money.email";
    private static final String EXCHANGE_MONEY_NAME = "docs_topic_ex";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_MONEY_NAME, MONEY_REFUND_ROUTING_KEY);
            channel.basicConsume(queueName, true, (tag, message) -> {
                try {
                    String templatePath = message.getEnvelope().getRoutingKey().replace('.', '/');
                    User u = User.deserialize(message.getBody());
                    String pdfPath = PdfGenerator.generatePdf(u, templatePath);
                    String emailAndPdfPath = u.getEmail() + ":" + pdfPath;
                    channel.basicPublish(EXCHANGE_MONEY_NAME, MONEY_EMAIL_ROUTING_KEY, null, emailAndPdfPath.getBytes());
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            }, tag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

package ru.itis.hw1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ru.itis.hw1.models.User;
import ru.itis.hw1.utils.UserFromConsole;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class Producer {
    // есть EXCHANGE - images НЕ ОЧЕРЕДЬ
    private final static String EXCHANGE_NAME = "pdf";
    // тип FANOUT
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.basicPublish(EXCHANGE_NAME, "",null, User.serialize(UserFromConsole.getUser()));
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException("Connection down");
        }
    }

}

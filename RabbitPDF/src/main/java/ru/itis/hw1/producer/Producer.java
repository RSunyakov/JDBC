package ru.itis.hw1.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ru.itis.hw1.models.User;
import ru.itis.hw1.utils.UserFromConsole;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class Producer {
    //fanout
    private final static String EXCHANGE_NAME = "pdf";
    private final static String EXCHANGE_TYPE = "fanout";

    //topic
    private final static String EXCHANGE_MONEY_NAME = "docs_topic_ex";
    private final static String EXCHANGE_MONEY_TYPE = "topic";
    private final static String MONEY_REFUND_ROUTING_KEY = "money.refund";
    private final static String MONEY_TRANSFER_ROUTING_KEY = "money.transfer";

    //direct
    private final static String EXCHANGE_DOP_NAME = "docs_direct_ex";
    private final static String EXCHANGE_DOP_TYPE = "direct";
    private final static String LICENSE_QUEUE_NAME = "license_queue";
    private final static String CREDIT_QUEUE_NAME = "credit_queue";
    private final static String LICENSE_QUEUE_BINDING_KEY = "police.license";
    private final static String CREDIT_QUEUE_BINDING_KEY = "bank.credit";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_MONEY_NAME, EXCHANGE_MONEY_TYPE);
            User user = User.builder().age(20).name("Roman").surname("Syunyakov").dateOfIssue("28/10/14").
                    email("srr1772@mail.ru").build();
            channel.basicPublish(EXCHANGE_MONEY_NAME, MONEY_REFUND_ROUTING_KEY, null, User.serialize(user));
            channel.basicPublish(EXCHANGE_MONEY_NAME, MONEY_TRANSFER_ROUTING_KEY, null, User.serialize(user));
            // fanaout exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.basicPublish(EXCHANGE_NAME, "",null, User.serialize(user));
            //topic exchange
            channel.exchangeDeclare(EXCHANGE_DOP_NAME, EXCHANGE_DOP_TYPE);
            channel.queueBind(LICENSE_QUEUE_NAME, EXCHANGE_DOP_NAME, LICENSE_QUEUE_BINDING_KEY);
            channel.queueBind(CREDIT_QUEUE_NAME, EXCHANGE_DOP_NAME, CREDIT_QUEUE_BINDING_KEY);
            channel.basicPublish(EXCHANGE_DOP_NAME, LICENSE_QUEUE_BINDING_KEY,null, User.serialize(user));
            channel.basicPublish(EXCHANGE_DOP_NAME, CREDIT_QUEUE_BINDING_KEY, null, User.serialize(user));
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException("Connection down", e);
        }
    }

}

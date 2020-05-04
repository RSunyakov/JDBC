package ru.kpfu.itis.javalabmessagequeue.producer;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import ru.kpfu.itis.javalabmessagequeue.models.Message;
import ru.kpfu.itis.javalabmessagequeue.models.MessageBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class JlmqProducer {
    @Autowired
    @Qualifier("producerStompSessionHandler")
    private StompSessionHandler stompSessionHandler;
    private StompSession stompSession;
    private String queueName;


    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;

    public void connect(String port) throws ExecutionException, InterruptedException {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(mappingJackson2MessageConverter);
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        this.stompSession = stompClient.connect("ws://localhost:" + port + "/producer", stompSessionHandler).get();

    }

    public JlmqProducer toQueue(String queueName) {
        this.queueName = queueName;
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.setDestination("/app/producer");
        stompHeaders.add("queueName", queueName);
        stompSession.send(stompHeaders, MessageBody.builder().body("romansunyakov@gmail.com").build());
        return this;
    }

    public void send(String message) {
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.setDestination("/app/producer_sent");
        stompHeaders.add("queueName", queueName);
        MessageBody messageSend = MessageBody.builder().body(message).build();
        stompSession.send(stompHeaders, messageSend);
    }

    public JlmqProducer create() {
        return this;
    }

}
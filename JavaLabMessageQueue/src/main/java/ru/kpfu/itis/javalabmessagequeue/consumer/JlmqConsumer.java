package ru.kpfu.itis.javalabmessagequeue.consumer;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
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
import ru.kpfu.itis.javalabmessagequeue.controllers.JlmqController;
import ru.kpfu.itis.javalabmessagequeue.models.Message;
import ru.kpfu.itis.javalabmessagequeue.producer.JlmqProducer;
import ru.kpfu.itis.javalabmessagequeue.producer.ProducerStompSessionHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class JlmqConsumer {
    @Autowired
    @Qualifier("consumerStompSessionHandler")
    private ConsumerStompSessionHandler stompSessionHandler;

    private StompSession stompSession;

    @Autowired
    private MappingJackson2MessageConverter mappingJackson2MessageConverter;

    public void connect(String port) throws ExecutionException, InterruptedException {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(mappingJackson2MessageConverter);
        this.stompSession = stompClient.connect("ws://localhost:" + port + "/consumer", stompSessionHandler).get();
    }

    public JlmqConsumer subscribe(String queueName) {
        StompHeaders stompHeaders = new StompHeaders();
        stompHeaders.setDestination("/jlmq/consumer_sent");
        stompHeaders.add("queueName", queueName);
        stompSession.subscribe(stompHeaders, stompSessionHandler);
        return this;
    }

    public JlmqConsumer create() {
        return this;
    }

    public JlmqConsumer onReceive(Consumer<Message> consumer) {
        consumer.accept(stompSessionHandler.getMessage());
        update();
        return this;
    }

    public void update() {
        stompSession.send("/app/updateMessage", stompSessionHandler.getMessage());
    }
}

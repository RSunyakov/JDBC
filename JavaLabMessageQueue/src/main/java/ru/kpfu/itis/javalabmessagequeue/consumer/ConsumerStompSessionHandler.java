package ru.kpfu.itis.javalabmessagequeue.consumer;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.javalabmessagequeue.models.Message;
import ru.kpfu.itis.javalabmessagequeue.models.MessageBody;

import java.lang.reflect.Type;

@Component
@Qualifier("consumerStompSessionHandler")
public class ConsumerStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(ConsumerStompSessionHandler.class);
    private StompSession stompSession;
    @Getter
    private Message message;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.stompSession = session;
        logger.info("New session established : " + session.getSessionId());
        logger.info("Message sent to websocket server");
    }
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message message = (Message) payload;
        System.out.println(message.getMessageId());
        message.setState("ACCEPTED");
        this.message = message;
        stompSession.send("/app/updateMessage", message);
        super.handleFrame(headers, payload);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }
}

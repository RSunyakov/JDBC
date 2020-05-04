package ru.kpfu.itis.javalabmessagequeue.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.javalabmessagequeue.models.MessageBody;

import java.lang.reflect.Type;

@Component
@Qualifier("producerStompSessionHandler")
public class ProducerStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(ProducerStompSessionHandler.class);


    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        logger.info("Message sent to websocket server");
    }
    //тут обрабатываем сообщения(хэдерс - наши команды, название очереди и т.д.)
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        super.handleFrame(headers, payload);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageBody.class;
    }
}

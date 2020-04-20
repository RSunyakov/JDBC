package ru.kpfu.itis.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.kpfu.itis.dto.MessageDto;
import ru.kpfu.itis.models.Message;
import ru.kpfu.itis.service.message.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String messageText = (String) message.getPayload();
        System.out.println(session.getId());
        MessageDto messageFromJson = objectMapper.readValue(messageText, MessageDto.class);

        if (!sessions.contains(session)) {
            sessions.add(session);
            List<MessageDto> messages = messageService.findByRoomId(Long.parseLong((String) session.getAttributes().get("roomId")));
            for (int i = 0; i < messages.size() ; i++){
                boolean isLast = (i == messages.size() - 1);
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(messages.get(i)), isLast));
            }
        }
        if (!messageFromJson.getText().equals("/start")) messageService.save(messageFromJson);

        for (WebSocketSession currentSession : sessions) {
            Long roomId = Long.parseLong((String) currentSession.getAttributes().get("roomId"));
            if (roomId.equals(messageFromJson.getRoomId())) {
                currentSession.sendMessage(message);
            }
        }
    }
}

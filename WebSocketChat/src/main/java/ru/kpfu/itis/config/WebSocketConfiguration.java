package ru.kpfu.itis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ru.kpfu.itis.handlers.AuthHandshakeHandler;
import ru.kpfu.itis.handlers.WebSocketMessagesHandler;

@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private WebSocketMessagesHandler handler;

    @Autowired
    private AuthHandshakeHandler handshakeHandler;

    @Autowired
    HandshakeInterceptor handshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler, "/chat/1", "/chat/2", "/chat/3")
                .setHandshakeHandler(handshakeHandler)
                .addInterceptors(handshakeInterceptor)
                .withSockJS();
    }
}
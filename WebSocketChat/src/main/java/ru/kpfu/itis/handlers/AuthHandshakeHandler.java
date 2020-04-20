package ru.kpfu.itis.handlers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.WebUtils;

import java.util.Map;

@Component
public class AuthHandshakeHandler implements HandshakeHandler {
    @Value("${jwt.secret}")
    private String secret;


    private DefaultHandshakeHandler defaultHandshakeHandler = new DefaultHandshakeHandler();
    @SneakyThrows
    @Override
    public boolean doHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws HandshakeFailureException {
        ServletServerHttpRequest request = (ServletServerHttpRequest)serverHttpRequest;
        String token = WebUtils.getCookie(request.getServletRequest(), "X-Authorization").getValue();
        Claims claims;
        try {
            // выполняю парсинг токена со своим секретным ключом
            claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody();
            return defaultHandshakeHandler.doHandshake(serverHttpRequest, serverHttpResponse, webSocketHandler, map);
        } catch (Exception e) {
            serverHttpResponse.setStatusCode(HttpStatus.FORBIDDEN);
            return false;
        }
    }
}

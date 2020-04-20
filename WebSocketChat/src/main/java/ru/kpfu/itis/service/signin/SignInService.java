package ru.kpfu.itis.service.signin;

import org.springframework.http.server.ServerHttpRequest;
import ru.kpfu.itis.dto.SignInDto;
import ru.kpfu.itis.dto.TokenDto;

import javax.servlet.http.HttpServletResponse;

public interface SignInService {
    boolean signIn(SignInDto form, HttpServletResponse response);
}

package ru.kpfu.itis.service.user;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserFromCookieService {
    UserDto getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}

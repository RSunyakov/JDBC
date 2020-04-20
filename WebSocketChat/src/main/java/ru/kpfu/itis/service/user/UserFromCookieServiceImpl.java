package ru.kpfu.itis.service.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import ru.kpfu.itis.dto.UserDto;
import ru.kpfu.itis.exception.ForbiddenException;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class UserFromCookieServiceImpl implements UserFromCookieService {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepository userRepository;

    @SneakyThrows
    @Override
    public UserDto getUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, "X-Authorization");
        if (cookie != null) {
            String token = cookie.getValue();
            Claims claims;
            try {
                // выполняю парсинг токена со своим секретным ключом
                claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                        .getBody();
                Optional<User> userOptional = userRepository.findById(Long.parseLong(claims.get("sub", String.class)));
                if (userOptional.isPresent()) {
                    return UserDto.from(userOptional.get());
                } else {
                    httpServletResponse.setStatus(403);
                    throw new ForbiddenException("Wrong userId");
                }
            } catch (Exception e) {
                httpServletResponse.setStatus(403);
                throw new ForbiddenException("Wrong token");
            }
        } else {
            httpServletResponse.setStatus(403);
            throw new ForbiddenException("Wrong cookie");
        }
    }
}

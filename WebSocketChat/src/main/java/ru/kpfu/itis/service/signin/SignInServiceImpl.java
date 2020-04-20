package ru.kpfu.itis.service.signin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;
import ru.kpfu.itis.dto.SignInDto;
import ru.kpfu.itis.dto.TokenDto;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;


    @Override
    public boolean signIn(SignInDto form, HttpServletResponse response) {
        Optional<User> userOptional = userRepository.findUserByEmail(form.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(form.getPassword(), user.getHashPassword())) {
                String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("name", user.getName())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                Cookie jwtToken = new Cookie("X-Authorization", token);
                jwtToken.setPath("/");
               response.addCookie(jwtToken);
               return true;
            } return false;
        } return false;
    }
}

package project.security.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import project.models.User;
import project.security.details.UserDetailsImpl;
import project.security.jwt.authentication.JwtAuthentication;
import project.services.AuthService;
import project.services.UserService;

@Component("jwtAuthenticationProvider")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        Claims claims;
        try {
            // выполняю парсинг токена со своим секретным ключом
             claims =  Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Bad token");
        }
        // создаем UserDetails
        UserDetails userDetails = UserDetailsImpl.builder()
                .user(userService.find(Long.parseLong(claims.get("sub", String.class))).get())
                .build();
        // аутентификация прошла успешно
        authentication.setAuthenticated(true);
        // положили в эту аутентификацию пользователя
        ((JwtAuthentication)authentication).setUserDetails(userDetails);
        return authentication;
    }

    // проверяет, подходит ли текущий провайдер для этой аутентификации
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
